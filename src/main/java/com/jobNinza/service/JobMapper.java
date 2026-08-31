package com.jobNinza.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import java.util.List;

public interface JobMapper {
    /**
     * Map JSON response to list of Jobs
     */
    List<Jobs> mapJobs(JsonNode root, Company company);
    
    /**
     * Get the ATS type this mapper supports
     */
    String getAtsType();
}