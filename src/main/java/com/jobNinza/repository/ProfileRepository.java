package com.jobNinza.repository;

import com.jobNinza.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
}
