package com.jobNinza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.Response.ResumeResponse;
import com.jobNinza.entity.Resume;
import com.jobNinza.service.ResumeService;
import com.jobNinza.util.ApiResponse;

@RestController
@RequestMapping("/resume")
public class ResumeController {
	@Autowired
	private ResumeService resumeService;
	
	@PostMapping("/uploadResume")
	 public ResponseEntity<ApiResponse<?>> uploadResume(
	            @RequestParam("file") MultipartFile file) {

	        Resume resume = resumeService.uploadResume(
	                file,3L
	                
	        );
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(
	                        new ApiResponse<>(
	                                "1",
	                                "Resume uploaded successfully"
	                        )
	                );
	    }

}
