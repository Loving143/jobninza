package com.jobNinza.util;
import java.time.Instant;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.enums.RemoteType;
import com.jobNinza.service.JobMapper;

public abstract class BaseJobMapper implements JobMapper {
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseJobMapper.class);

    /**
     * Set common fields for all jobs
     */
    protected void setCommonFields(Jobs job, Company company, String atsType) {
        job.setCompany(company);
        job.setSource(atsType);
        job.setSourceAtsType(atsType);
        job.setScrapedAt(Instant.now());
        job.setActive(true);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedDate(LocalDateTime.now());
        
        // Set default values if missing
        if (job.getRemoteType() == null) {
            job.setRemoteType(RemoteType.NOT_SPECIFIED);
        }
    }

    /**
     * Parse date string to Instant
     */
    protected Instant parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return Instant.now();
        }
        
        try {
            return Instant.parse(dateString);
        } catch (Exception e) {
            try {
                return Instant.parse(dateString.replace(" ", "T") + "Z");
            } catch (Exception ex) {
                logger.debug("Failed to parse date: {}", dateString);
                return Instant.now();
            }
        }
    }

    /**
     * Parse location string into components and set on job
     */
    protected void parseLocation(String location, Jobs job) {
        if (location == null || location.isEmpty()) {
            return;
        }
        
        String[] parts = location.split(",");
        if (parts.length >= 1) {
            job.setCity(parts[0].trim());
        }
        if (parts.length >= 2) {
            job.setState(parts[1].trim());
        }
        if (parts.length >= 3) {
            job.setCountry(parts[2].trim());
        }
    }

    /**
     * Build location string from components
     */
    protected String buildLocationString(String city, String state, String country) {
        StringBuilder location = new StringBuilder();
        if (city != null && !city.isEmpty()) {
            location.append(city);
        }
        if (state != null && !state.isEmpty()) {
            if (location.length() > 0) location.append(", ");
            location.append(state);
        }
        if (country != null && !country.isEmpty()) {
            if (location.length() > 0) location.append(", ");
            location.append(country);
        }
        return location.toString();
    }

    /**
     * Map workplace type to RemoteType enum
     */
    protected void mapWorkplaceType(String workplaceType, Jobs job) {
        if (workplaceType != null && !workplaceType.isEmpty()) {
            job.setWorkplaceType(workplaceType);
            job.setRemoteType(RemoteType.fromString(workplaceType));
            job.setIsRemote(job.getRemoteType().allowsRemote());
        }
    }

    /**
     * Map employment type to EmploymentType enum
     */
    protected void mapEmploymentType(String employmentType, Jobs job) {
        if (employmentType != null && !employmentType.isEmpty()) {
            job.setEmploymentTypeString(employmentType);
//            job.setEmploymentType(EmploymentType.fromString(employmentType));
        }
    }
}