package com.jobNinza.mapper;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class JobviteJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "Jobvite";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        
        JsonNode jobsNode = root.path("jobs");
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
            String location = jobNode.path("location").asText();
            job.setLocation(location);
            parseLocation(location, job);
            
            // Workplace Type
            String workplaceType = jobNode.path("workplaceType").asText();
            mapWorkplaceType(workplaceType, job);
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // URLs
            job.setJobUrl(jobNode.path("applyUrl").asText());
            job.setAbsoluteUrl(jobNode.path("applyUrl").asText());
            job.setApplicationUrl(jobNode.path("applyUrl").asText());
            
            // Dates
            String postedDate = jobNode.path("postedDate").asText();
            job.setPostedAt(parseDate(postedDate));
            
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