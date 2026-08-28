package com.jobNinza.controller;

import com.jobNinza.entity.Profile;
import com.jobNinza.entity.ProfileSkill;
import com.jobNinza.entity.Skill;
import com.jobNinza.service.ProfileService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume")
public class ResumeController {
	@Autowired
	private ResumeService resumeService;
	@Autowired
	private ProfileService profileService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/uploadResume")
	 public ResponseEntity<ApiResponse<?>> uploadResume(
	            @RequestParam("file") MultipartFile file) {
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

}
