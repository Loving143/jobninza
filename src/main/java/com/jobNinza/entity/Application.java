package com.jobNinza.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import com.jobNinza.enums.ApplicationMethod;
import com.jobNinza.enums.ApplicationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

    Users user;

    Job job;

    Resume resume;

    ApplicationStatus status;

    String applicationUrl;

    Instant appliedAt;

    ApplicationMethod applicationMethod;

//    AutomationStatus automationStatus;

    String notes;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
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
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Resume getResume() {
		return resume;
	}
	public void setResume(Resume resume) {
		this.resume = resume;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	public Instant getAppliedAt() {
		return appliedAt;
	}
	public void setAppliedAt(Instant appliedAt) {
		this.appliedAt = appliedAt;
	}
	public ApplicationMethod getApplicationMethod() {
		return applicationMethod;
	}
	public void setApplicationMethod(ApplicationMethod applicationMethod) {
		this.applicationMethod = applicationMethod;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
}