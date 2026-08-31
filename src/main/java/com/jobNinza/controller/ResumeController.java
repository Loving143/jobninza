package com.jobNinza.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.jobNinza.entity.Profile;
import com.jobNinza.entity.ProfileSkill;
import com.jobNinza.entity.Skill;
import com.jobNinza.service.ATSSyncService;
import com.jobNinza.service.JobAggregationService;
import com.jobNinza.service.ProfileService;
import com.jobNinza.service.ResumeService;
import com.jobNinza.util.ApiResponse;
import com.jobNinza.util.ResumeData;

@RestController
@RequestMapping("/resume")
public class ResumeController {
	@Autowired
	private ResumeService resumeService;
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private ATSSyncService atsSyncService;
	
	@Autowired
	private JobAggregationService JobAggregationService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/uploadResume")
	 public ResponseEntity<ApiResponse<?>> uploadResume(@RequestParam("file") MultipartFile file) {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String email = authToken.getName();
		ResumeData resume = resumeService.uploadResume(file,email);
		Profile profile = new Profile(resume);
		List<Skill> skills = resume.getSkills().stream().map(Skill::new).collect(Collectors.toList());
		for(Skill skill:skills) { 
			ProfileSkill profileSkill = new ProfileSkill();
			profileSkill.setProfile(profile);
			profileSkill.setSkill(skill);
		}
		profileService.saveProfile(profile);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("1",resume));
	    }
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/fetchCompanyDetails")
	public ResponseEntity<?> fetchCompanyDetails(){
		return ResponseEntity.ok(new ApiResponse<>("1",atsSyncService.fetchCompanies()));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/getJobs")
	public ResponseEntity<?> fetchJobs(){
		return ResponseEntity.ok(new ApiResponse<>("1",JobAggregationService.aggregateJobsForAllCompanies()));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/saveJobs")
	public ResponseEntity<?> fetchAndSaveJobs(){
		JobAggregationService.aggregateJobs();
		return ResponseEntity.ok(new ApiResponse<>("1","Jobs saved successfully!!"));
	}

}
