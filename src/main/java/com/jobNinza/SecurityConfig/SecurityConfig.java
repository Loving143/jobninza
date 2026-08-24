package com.jobNinza.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jobNinza.security.CustomAccessDeniedHandler;
import com.jobNinza.security.CustomAuthenticationEntryPoint;
import com.jobNinza.security.CustomAuthenticationProvider;
import com.jobNinza.security.JwtAuthenticationFilter;
import com.jobNinza.security.OtpAuthenticationProvider;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean 
    public AuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider();
    }
    
    
    @Bean 
    public AuthenticationProvider otpAuthenticationProvider() {
    	return new OtpAuthenticationProvider();
    }
    
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
	 public AccessDeniedHandler customAccessDeniedHandler() {
	      return new CustomAccessDeniedHandler(); // Ensure that the custom handler is defined as a bean
	   }
    
    @Bean
	 public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
	      return new CustomAuthenticationEntryPoint(); // Ensure that the custom handler is defined as a bean
	   }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        		.authenticationProvider(otpAuthenticationProvider())
        		.authenticationProvider(customAuthenticationProvider())
                
               
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",
                    "/public/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception
                .accessDeniedHandler(customAccessDeniedHandler())
                .authenticationEntryPoint(customAuthenticationEntryPoint())
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            );

        return http.build();
    }
}
