package com.jobNinza.service;

import com.jobNinza.entity.Company;
import com.jobNinza.enums.ATSType;

public interface ATSClient {
	 String getAtsType();
	 String fetchJobs(Company company);
}
