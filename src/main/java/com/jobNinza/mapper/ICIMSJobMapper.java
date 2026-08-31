package com.jobNinza.mapper;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.enums.RemoteType;
import com.jobNinza.util.BaseJobMapper;

@Component
public class ICIMSJobMapper extends BaseJobMapper {

    private static final String ATS_TYPE = "iCIMS";

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
            job.setDepartment(jobNode.path("department").path("name").asText());
            
            // Location
            JsonNode locationNode = jobNode.path("location");
            String locationName = locationNode.path("name").asText();
            job.setLocation(locationName);
            job.setCity(locationNode.path("city").asText());
            job.setState(locationNode.path("state").asText());
            job.setCountry(locationNode.path("country").asText());
            
            // Remote type
            boolean isRemote = jobNode.path("remote").asBoolean(false);
            if (isRemote) {
                job.setRemoteType(RemoteType.REMOTE);
                job.setIsRemote(true);
                job.setWorkplaceType("Remote");
            } else {
                String workType = jobNode.path("workType").asText();
                if ("hybrid".equalsIgnoreCase(workType)) {
                    job.setRemoteType(RemoteType.HYBRID);
                    job.setIsRemote(true);
                    job.setWorkplaceType("Hybrid");
                } else {
                    job.setRemoteType(RemoteType.ON_SITE);
                    job.setIsRemote(false);
                    job.setWorkplaceType("On-site");
                }
            }
            
            // Employment type
            String employmentType = jobNode.path("employmentType").asText();
            mapEmploymentType(employmentType, job);
            
            // URLs
            job.setJobUrl(jobNode.path("url").asText());
            job.setAbsoluteUrl(jobNode.path("url").asText());
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