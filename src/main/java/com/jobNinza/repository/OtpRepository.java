package com.jobNinza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobNinza.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

	Optional<Otp> findByUserNameAndOtp(String username, String otpCode);

}
