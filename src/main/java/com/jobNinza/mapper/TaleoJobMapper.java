package com.jobNinza.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class TaleoJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "Taleo";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        
        JsonNode jobsNode = root.path("jobRequisitions");
        if (jobsNode == null || !jobsNode.isArray()) {
            return jobs;
        }

        for (JsonNode jobNode : jobsNode) {
            Jobs job = new Jobs();
            
            // Core IDs
            job.setExternalJobId(jobNode.path("requisitionId").asText());
            job.setRequisitionId(jobNode.path("requisitionId").asText());
            
            // Basic info
            job.setTitle(jobNode.path("title").asText());
            job.setDescription(jobNode.path("description").asText());
            
            // Location
            JsonNode locationNode = jobNode.path("location");
            String location = locationNode.path("name").asText();
            if (location.isEmpty()) {
                location = jobNode.path("city").asText();
            }
            job.setLocation(location);
            job.setCity(jobNode.path("city").asText());
            job.setState(jobNode.path("state").asText());
            job.setCountry(jobNode.path("country").asText());
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // Dates
            String postedDate = jobNode.path("postedDate").asText();
            job.setPostedAt(parseDate(postedDate));
            job.setApplicationDeadline(jobNode.path("closeDate").asText());
            
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