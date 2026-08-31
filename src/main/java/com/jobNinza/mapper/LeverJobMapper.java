package com.jobNinza.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class LeverJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "Lever";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        
        // Lever returns array directly
        if (!root.isArray()) {
            return jobs;
        }

        for (JsonNode jobNode : root) {
            Jobs job = new Jobs();
            
            // Core IDs
            job.setExternalJobId(jobNode.path("id").asText());
            job.setRequisitionId(jobNode.path("reqId").asText());
            
            // Basic info
            job.setTitle(jobNode.path("text").asText());
            job.setDescription(jobNode.path("description").asText());
            job.setCompanyName(jobNode.path("company").path("name").asText());
            
            // Location
            JsonNode locationNode = jobNode.path("location");
            String city = locationNode.path("city").asText();
            String state = locationNode.path("state").asText();
            String country = locationNode.path("country").asText();
            
            job.setCity(city);
            job.setState(state);
            job.setCountry(country);
            job.setLocation(buildLocationString(city, state, country));
            
            // Workplace Type
            String workplaceType = jobNode.path("workplaceType").asText();
            mapWorkplaceType(workplaceType, job);
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // URLs
            job.setJobUrl(jobNode.path("hostedUrl").asText());
            job.setAbsoluteUrl(jobNode.path("hostedUrl").asText());
            job.setApplicationUrl(jobNode.path("applyUrl").asText());
            
            // Dates
            String createdAt = jobNode.path("createdAt").asText();
            job.setPostedAt(parseDate(createdAt));
            
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