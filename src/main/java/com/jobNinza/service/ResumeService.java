package com.jobNinza.service;

import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.entity.Resume;

public interface ResumeService {

	Resume uploadResume(MultipartFile file, long l);

}
