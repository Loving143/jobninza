package com.jobNinza.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.util.BaseJobMapper;

@Component
public class GreenhouseJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "Greenhouse";

    @Override
    public List<Jobs> mapJobs(JsonNode root, Company company) {
        List<Jobs> jobs = new ArrayList<>();
        JsonNode jobsNode = root.get("jobs");
        
        if (jobsNode == null || !jobsNode.isArray()) {
            return jobs;
        }

        for (JsonNode jobNode : jobsNode) {
            Jobs job = new Jobs();
            
            // Core IDs
            job.setExternalJobId(jobNode.path("id").asText());
            job.setRequisitionId(jobNode.path("requisition_id").asText());
            job.setInternalJobId(jobNode.path("internal_job_id").asLong());
            
            // Basic info
            job.setTitle(jobNode.path("title").asText());
            job.setCompanyName(jobNode.path("company_name").asText());
            job.setLanguage(jobNode.path("language").asText());
            
            // Location
            JsonNode locationNode = jobNode.path("location");
            String locationName = locationNode.path("name").asText();
            job.setLocation(locationName);
            parseLocation(locationName, job);
            
            // Workplace Type from metadata
            JsonNode metadata = jobNode.path("metadata");
            for (JsonNode meta : metadata) {
                String metaName = meta.path("name").asText();
                if ("Workplace Type".equals(metaName)) {
                    String workplaceType = meta.path("value").asText();
                    mapWorkplaceType(workplaceType, job);
                    break;
                }
            }
            
            // Employment type
            String employmentType = jobNode.path("employment_type").asText();
            mapEmploymentType(employmentType, job);
            
            // URLs
            job.setAbsoluteUrl(jobNode.path("absolute_url").asText());
            job.setJobUrl(jobNode.path("absolute_url").asText());
            job.setApplicationUrl(jobNode.path("absolute_url").asText());
            
            // Dates
            String firstPublished = jobNode.path("first_published").asText();
            job.setFirstPublished(firstPublished);
//            job.setPostedAt(parseDate(firstPublished));
            
            String updatedAt = jobNode.path("updated_at").asText();
//            job.setUpdatedAt(parseDate(updatedAt));
            
            job.setApplicationDeadline(jobNode.path("application_deadline").asText());
            
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