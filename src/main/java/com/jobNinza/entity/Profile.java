package com.jobNinza.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jobNinza.util.ExperienceDto;
import com.jobNinza.util.ResumeData;
import jakarta.persistence.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
    private Users user;
	String headline;
    String summary;
    Long totalExperience;
    String currentCompany;
    String currentDesignation;
    String currentLocation;
    Integer noticePeriod;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Experience> experiences =new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY)
	private List<Project>projects = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY)
	private List<Skill>skills = new ArrayList<>();

	public Profile(ResumeData resume) {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		this.createdAt = LocalDateTime.now();
		this.currentCompany = resume.getExperience().get(0).getCompany();
		this.currentDesignation = resume.getExperience().get(0).getDesignation();
		this.noticePeriod =30;
		this.totalExperience = resume.getExperience().stream().mapToLong(e -> {
			LocalDate start =null;
			if(e.getStartDate()!=null) {
				start = LocalDate.parse(e.getStartDate());
			}
			LocalDate end = null;
			if (e.getEndDate() == null || e.getEndDate().equalsIgnoreCase("Present")) {
				end = LocalDate.now();
			} else {
				end = LocalDate.parse(e.getEndDate());
			}
				return ChronoUnit.MONTHS.between(YearMonth.from(start),YearMonth.from(end));
		}).sum();
		this.currentLocation = resume.getLocation();
		this.summary = resume.getSummary() != null
				? Arrays.stream(resume.getSummary().split("\\s+"))
				.limit(249)
				.collect(Collectors.joining(" "))
				: null;
		this.experiences = resume.getExperience().stream().map(Experience::new).collect(Collectors.toList());
		this.projects = resume.getProjects().stream().map(Project::new).collect(Collectors.toList());
		this.skills = resume.getSkills().stream().map(Skill::new).collect(Collectors.toList());
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(Long totalExperience) {
		this.totalExperience = totalExperience;
	}
	public String getCurrentCompany() {
		return currentCompany;
	}
	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}
	public String getCurrentDesignation() {
		return currentDesignation;
	}
	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public Integer getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(Integer noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Experience> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
