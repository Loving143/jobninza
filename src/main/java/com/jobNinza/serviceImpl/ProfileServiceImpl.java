package com.jobNinza.serviceImpl;

import com.jobNinza.entity.Profile;
import com.jobNinza.repository.ProfileRepository;
import com.jobNinza.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }
}
