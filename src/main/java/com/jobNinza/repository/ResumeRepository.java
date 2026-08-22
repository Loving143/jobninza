package com.jobNinza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobNinza.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
