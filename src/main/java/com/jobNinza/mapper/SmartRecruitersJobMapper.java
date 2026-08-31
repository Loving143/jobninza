package com.jobNinza.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class SmartRecruitersJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "SmartRecruiters";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        
        JsonNode jobsNode = root.path("content");
        if (jobsNode == null || !jobsNode.isArray()) {
            return jobs;
        }

        for (JsonNode jobNode : jobsNode) {
            Jobs job = new Jobs();
            
            // Core IDs
            job.setExternalJobId(jobNode.path("id").asText());
            
            // Basic info
            job.setTitle(jobNode.path("name").asText());
            job.setDescription(jobNode.path("description").asText());
            job.setCompanyName(jobNode.path("companyName").asText());
            job.setDepartment(jobNode.path("department").path("name").asText());
            
            // Location
            JsonNode locationNode = jobNode.path("location");
            String locationName = locationNode.path("address").asText();
            if (locationName.isEmpty()) {
                locationName = locationNode.path("city").asText() + ", " + locationNode.path("country").asText();
            }
            job.setLocation(locationName);
            parseLocation(locationName, job);
            
            // Workplace Type
            String workplaceType = jobNode.path("type").asText();
            mapWorkplaceType(workplaceType, job);
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // URLs
            job.setJobUrl(jobNode.path("url").asText());
            job.setAbsoluteUrl(jobNode.path("url").asText());
            job.setApplicationUrl(jobNode.path("applyUrl").asText());
            
            // Dates
            String startDate = jobNode.path("startDate").asText();
            job.setPostedAt(parseDate(startDate));
            job.setFirstPublished(startDate);
            
            // Store raw data
            job.setRawData(jobNode.toString());
            
            // Set common fields
            setCommonFields(job, company, ATS_TYPE);
            
            jobs.add(job);
        }
        
        return jobs;
    }

    @Override
    public String getAtsType() {
        return ATS_TYPE;
    }
}