package com.jobNinza.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.Response.ATSAPIResponse;
import com.jobNinza.Response.CompanyATS;
import com.jobNinza.entity.Company;
import com.jobNinza.repository.CompanyRepository;
import com.jobNinza.service.ATSSyncService;
@Service
public class ATSSyncServiceImpl implements ATSSyncService {
	 private static final String API_URL = "https://withresumeai.com/api/v1/ats";
	    private final ObjectMapper objectMapper = new ObjectMapper();
	    private final CompanyRepository companyRepository;
	    public ATSSyncServiceImpl(CompanyRepository companyRepository) {
	        this.companyRepository = companyRepository;
	    }
	    public List<CompanyATS> fetchCompanies() {
	        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	            HttpGet request = new HttpGet(API_URL);
	            request.setHeader("Accept", "application/json");
	            request.setHeader("User-Agent", "JobSearchApp/1.0");
	            
	            try (CloseableHttpResponse response = httpClient.execute(request)) {
	                int statusCode = response.getCode();
	                if (statusCode != 200) {
	                    throw new RuntimeException("API returned status code: " + statusCode);
	                }
	                
	                String jsonResponse;
					try {
						jsonResponse = EntityUtils.toString(response.getEntity());
					
	                ATSAPIResponse apiResponse = objectMapper.readValue(jsonResponse, ATSAPIResponse.class);
	                saveCompany(apiResponse);
	                Company company = new Company();
	                return apiResponse.getCompanies();
					} catch (ParseException e) {
						e.printStackTrace();
					}
	            }
	        } catch (IOException e) {
	        	
			}
	        return null;
	    }
	    
	    public void saveCompany(ATSAPIResponse apiResponse) {
	        List<CompanyATS> companies = apiResponse.getCompanies();
	        for (CompanyATS companyATS : companies) {
	            Company company = new Company();
	            company.setCompanyName(companyATS.getCompany());
	            company.setAts(companyATS.getAts());
	            company.setSlug(companyATS.getSlug());
	            company.setApplyHost(companyATS.getApplyHost());
	            company.setVerified(companyATS.isVerified());
	            companyRepository.save(company);
	        }
	    }
}
