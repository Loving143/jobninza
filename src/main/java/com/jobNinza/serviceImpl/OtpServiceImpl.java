package com.jobNinza.serviceImpl;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobNinza.entity.Otp;
import com.jobNinza.repository.OtpRepository;
import com.jobNinza.service.OtpService;
@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;
	private final SecureRandom secureRandom = new SecureRandom();
	@Override
	public Otp generateOtp(String userName) {
		String otpCode = String.format("%06d", new Random().nextInt(999999));
        Otp otp = new Otp();
        otp.setUserName(userName);
        otp.setOtp(otpCode);
        otp.setExpiresAt(Instant.now().plus(Duration.ofMinutes(5))); // 5 minutes expiry
//        otp.setAttempts(null);
        otp.setType("EMAIL");
        return otpRepository.save(otp);
	}

}
