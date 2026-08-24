package com.jobNinza.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResumeExtractorService {

	String extractText(MultipartFile file);

}
