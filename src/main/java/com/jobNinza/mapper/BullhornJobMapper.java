package com.jobNinza.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class BullhornJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "Bullhorn";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        
        JsonNode jobsNode = root.path("data");
        if (jobsNode == null || !jobsNode.isArray()) {
            return jobs;
        }

        for (JsonNode jobNode : jobsNode) {
            Jobs job = new Jobs();
            
            // Core IDs
            job.setExternalJobId(jobNode.path("id").asText());
            
            // Basic info
            job.setTitle(jobNode.path("title").asText());
            job.setDescription(jobNode.path("description").asText());
            
            // Location
            JsonNode addressNode = jobNode.path("address");
            String address = addressNode.path("address1").asText() + " " + 
                            addressNode.path("city").asText() + " " + 
                            addressNode.path("state").asText();
            job.setLocation(address.trim());
            job.setCity(addressNode.path("city").asText());
            job.setState(addressNode.path("state").asText());
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // Dates
            String dateAdded = jobNode.path("dateAdded").asText();
            job.setPostedAt(parseDate(dateAdded));
            
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