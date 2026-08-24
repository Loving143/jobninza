package com.jobNinza.security;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jobNinza.entity.Users;
import com.jobNinza.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username)  {
		Users user = userRepository.findByEmail(username).orElseThrow(()-> 
		new UsernameNotFoundException("User with username not found!"));
		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}


}
