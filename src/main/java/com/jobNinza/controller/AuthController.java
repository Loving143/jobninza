package com.jobNinza.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobNinza.Response.LoginResponse;
import com.jobNinza.entity.Otp;
import com.jobNinza.request.LoginRequest;
import com.jobNinza.request.SignupRequest;
import com.jobNinza.request.ValidateOtpRequest;
import com.jobNinza.security.CustomUserDetails;
import com.jobNinza.security.JwtUtil;
import com.jobNinza.service.OtpService;
import com.jobNinza.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final OtpService otpService;
	private final UserService userService;
	private final UserDetailsService userDetailsService;
	AuthController(AuthenticationManager authenticationManager,JwtUtil jwtUtil, OtpService otpService, UserDetailsService userDetailsService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.otpService = otpService;
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
		authenticationManager.authenticate(auth);
//		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		Otp otp = otpService.generateOtp(userName);
		System.out.println(otp.getOtp()+" This is otp");
		return ResponseEntity.ok("An otp has been sent to the email");
}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody ValidateOtpRequest otpReq) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(otpReq.getUserName(), otpReq.getOtp()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    CustomUserDetails userDetails =(CustomUserDetails) userDetailsService.loadUserByUsername(otpReq.getUserName());
	    String jwtToken = jwtUtil.generateToken(otpReq.getUserName(),userDetails.getAuthorities());
	    LoginResponse response = new LoginResponse(jwtToken,otpReq.getUserName());
	    return ResponseEntity.ok().body(response);
}
	 @PostMapping("/signup")
	    public ResponseEntity<?>signUp(@RequestBody SignupRequest req){
	    	userService.signup(req);
	    	return ResponseEntity.ok("User registered successfully!");
	    }
}
