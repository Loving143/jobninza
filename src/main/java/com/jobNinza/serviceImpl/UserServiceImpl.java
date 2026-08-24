package com.jobNinza.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobNinza.entity.Users;
import com.jobNinza.repository.UserRepository;
import com.jobNinza.request.SignupRequest;
import com.jobNinza.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder ;
	private final UserRepository userRepository;
	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder=passwordEncoder;
		this.userRepository = userRepository;
	}
	@Override
	public void signup(SignupRequest req) {
		Users user = new Users(req);
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		userRepository.save(user);
	}

}
