package com.jobNinza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobNinza.entity.Jobs;
public interface JobRepository extends JpaRepository<Jobs,Long>{

}
