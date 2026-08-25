package com.jobNinza.controller;

import com.jobNinza.util.ResumeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.entity.Resume;
import com.jobNinza.service.ResumeService;
import com.jobNinza.util.ApiResponse;

@RestController
@RequestMapping("/resume")
public class ResumeController {
	@Autowired
	private ResumeService resumeService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/uploadResume")
	 public ResponseEntity<ApiResponse<?>> uploadResume(
	            @RequestParam("file") MultipartFile file) {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String email = authToken.getName();
		ResumeData resume = resumeService.uploadResume(file,email);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("1",resume));
	    }

}
