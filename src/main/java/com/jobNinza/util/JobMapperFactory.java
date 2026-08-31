package com.jobNinza.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobNinza.service.JobMapper;

@Component
public class JobMapperFactory {

    private final Map<String, JobMapper> mapperMap = new ConcurrentHashMap<>();

    @Autowired
    public JobMapperFactory(java.util.List<JobMapper> mappers) {
        for (JobMapper mapper : mappers) {
            mapperMap.put(mapper.getAtsType().toLowerCase(), mapper);
        }
    }

    public JobMapper getMapper(String atsType) {
        if (atsType == null || atsType.isEmpty()) {
            throw new IllegalArgumentException("ATS type cannot be null or empty");
        }

        JobMapper mapper = mapperMap.get(atsType.toLowerCase());
        if (mapper == null) {
            throw new IllegalArgumentException("No mapper found for ATS type: " + atsType);
        }

        return mapper;
    }

    public boolean hasMapper(String atsType) {
        return atsType != null && mapperMap.containsKey(atsType.toLowerCase());
    }

    public Map<String, JobMapper> getAllMappers() {
        return new ConcurrentHashMap<>(mapperMap);
    }
}