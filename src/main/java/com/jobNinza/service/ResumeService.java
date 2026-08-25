package com.jobNinza.service;

import com.jobNinza.util.ResumeData;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.entity.Resume;

public interface ResumeService {

	ResumeData uploadResume(MultipartFile file, String email);

}
