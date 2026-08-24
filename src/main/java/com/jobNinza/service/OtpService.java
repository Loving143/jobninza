package com.jobNinza.service;

import com.jobNinza.entity.Otp;

public interface OtpService {

	Otp generateOtp(String userName);

}
