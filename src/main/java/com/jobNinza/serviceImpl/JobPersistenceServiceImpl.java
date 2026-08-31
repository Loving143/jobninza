package com.jobNinza.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobNinza.entity.Jobs;
import com.jobNinza.repository.JobRepository;
import com.jobNinza.service.JobPersistenceService;

@Service
public class JobPersistenceServiceImpl implements JobPersistenceService {

	@Autowired
    private JobRepository jobRepository ;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Jobs saveJob(Jobs job) {
        try {
        	Jobs savedJob = jobRepository.save(job);
        	return savedJob;
        } catch (Exception e) {

        }
        return null;
    }
}
