package com.jobNinza.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.jobNinza.entity.Otp;
import com.jobNinza.repository.OtpRepository;

public class OtpAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private OtpRepository otpRepository;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otpCode = authentication.getCredentials().toString();
        Otp otp = otpRepository.findByUserNameAndOtp(username, otpCode).orElseThrow(() -> new BadCredentialsException("Invalid OTP"));
        if (otpCode.equals( otp.getOtp())) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),new ArrayList<>());
        } else {
            throw new BadCredentialsException("Invalid OTP");
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
