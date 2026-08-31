package com.jobNinza.service;

import java.util.List;

public interface JobAggregationService {

    void aggregateJobs();
    List<String> aggregateJobsForAllCompanies();
    
}
