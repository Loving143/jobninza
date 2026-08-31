package com.jobNinza.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.repository.CompanyRepository;
import com.jobNinza.repository.JobRepository;
import com.jobNinza.service.ATSClient;
import com.jobNinza.service.JobAggregationService;
import com.jobNinza.service.JobMapper;
import com.jobNinza.service.JobPersistenceService;
import com.jobNinza.util.ATSClientFactory;
import com.jobNinza.util.JobMapperFactory;

@Service
public class JobAggregationServiceImpl implements JobAggregationService {

    private static final Logger logger = LoggerFactory.getLogger(JobAggregationServiceImpl.class);
    
    private final JobPersistenceService jobPersistenceService;
    
    private final CompanyRepository companyRepository;
    private final ATSClientFactory atsClientFactory;
    private final JobRepository jobRepository;
    private final JobMapperFactory jobMapperFactory;
    private final ObjectMapper objectMapper;

    public JobAggregationServiceImpl(
            CompanyRepository companyRepository,
            ATSClientFactory atsClientFactory,
            JobRepository jobRepository,
            JobMapperFactory jobMapperFactory,
            ObjectMapper objectMapper,JobPersistenceService jobPersistenceService) {
    	this.jobPersistenceService = jobPersistenceService;
    	this.companyRepository = companyRepository;
        this.atsClientFactory = atsClientFactory;
        this.jobRepository = jobRepository;
        this.jobMapperFactory = jobMapperFactory;
        this.objectMapper = objectMapper;
    }

    	@Override
    	public void aggregateJobs() {

    	    logger.info("Starting job aggregation process...");

    	    List<Company> companies = companyRepository.findByActiveTrue();

    	    if (companies.isEmpty()) {
    	        logger.warn("No active companies found to aggregate jobs");
    	        return;
    	    }

    	    int successCount = 0;
    	    int failureCount = 0;
    	    int totalJobsSaved = 0;

    	    for (Company company : companies) {

    	        try {

    	            logger.info(
    	                "Processing company: {} (ATS: {})",
    	                company.getCompanyName(),
    	                company.getAts()
    	            );

    	            // 1. Get ATS client
    	            ATSClient client = atsClientFactory.getClient(company.getAts());

    	            if (client == null) {

    	                logger.error(
    	                    "No ATS client found for company: {} with ATS type: {}",
    	                    company.getCompanyName(),
    	                    company.getAts()
    	                );

    	                failureCount++;
    	                continue;
    	            }

    	            // 2. Fetch jobs
    	            String jsonResponse = client.fetchJobs(company);

    	            if (jsonResponse == null
    	                    || jsonResponse.isBlank()
    	                    || "{}".equals(jsonResponse)) {

    	                logger.warn(
    	                    "No jobs data received for company: {}",
    	                    company.getCompanyName()
    	                );

    	                failureCount++;
    	                continue;
    	            }

    	            // 3. Mapper
    	            JobMapper mapper = jobMapperFactory.getMapper(company.getAts());

    	            JsonNode root = objectMapper.readTree(jsonResponse);

    	            List<Jobs> jobs = mapper.mapJobs(root, company);

    	            if (jobs.isEmpty()) {

    	                logger.info(
    	                    "No jobs parsed for company: {}",
    	                    company.getCompanyName()
    	                );

    	                continue;
    	            }

    	            // 4. Save each job independently
    	            int companyJobsSaved = 0;

    	            for (Jobs job : jobs) {

    	                try {

    	                    Jobs savedJob = jobPersistenceService.saveJob(job);

    	                    if (savedJob != null) {
    	                        companyJobsSaved++;
    	                        totalJobsSaved++;
    	                    }

    	                } catch (Exception e) {

    	                    failureCount++;

    	                    logger.error(
    	                        "Failed to save job for company: {}. Job title: {}. Error: {}",
    	                        company.getCompanyName(),
    	                        job.getTitle(),
    	                        e.getMessage(),
    	                        e
    	                    );
    	                }
    	            }

    	            if (companyJobsSaved > 0) {
    	                successCount++;
    	            }

    	            logger.info(
    	                "Company {} completed. Saved: {} / {} jobs",
    	                company.getCompanyName(),
    	                companyJobsSaved,
    	                jobs.size()
    	            );

    	        } catch (Exception e) {

    	            failureCount++;

    	            logger.error(
    	                "Failed to process company: {} - Error: {}",
    	                company.getCompanyName(),
    	                e.getMessage(),
    	                e
    	            );
    	        }
    	    }

    	    logger.info(
    	        "Job aggregation completed. Total Jobs Saved: {}, Success Companies: {}, Failed Jobs/Companies: {}",
    	        totalJobsSaved,
    	        successCount,
    	        failureCount
    	    );
    	}
	

    @Override
    @Transactional
    public List<String> aggregateJobsForAllCompanies() {
        List<Company> companies = companyRepository.findByActiveTrue();
        List<String> jobList = new ArrayList<>();
        
        if (companies.isEmpty()) {
            logger.warn("No active companies found");
            return jobList;
        }

        for (Company company : companies) {
            try {
                ATSClient client = atsClientFactory.getClient(company.getAts());
                
                if (client == null) {
                    logger.error("No ATS client found for company: {}", company.getCompanyName());
                    continue;
                }
                
                String jobs = client.fetchJobs(company);
                
                if (jobs != null && !jobs.isEmpty() && !"{}".equals(jobs)) {
                    jobList.add(jobs);
                    logger.debug("Fetched jobs for company: {}", company.getCompanyName());
                } else {
                    logger.warn("No jobs data for company: {}", company.getCompanyName());
                }
                
            } catch (Exception e) {
                logger.error("Failed to fetch jobs for company: {} - Error: {}", 
                    company.getCompanyName(), e.getMessage(), e);
            }
        }
        
        logger.info("Fetched jobs for {} companies", jobList.size());
        return jobList;
    }

    }

    