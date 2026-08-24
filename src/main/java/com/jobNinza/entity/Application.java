package com.jobNinza.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jobNinza.enums.ApplicationMethod;
import com.jobNinza.enums.ApplicationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

	@ManyToOne
    Users user;
    
    @OneToMany(fetch = FetchType.LAZY)
    List<Jobs> job=new ArrayList();

    @ManyToOne
    Resume resume;

    @Enumerated(EnumType.STRING)
    ApplicationStatus status;

    String applicationUrl;

    Instant appliedAt;

    @Enumerated(EnumType.STRING)
    ApplicationMethod applicationMethod;

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
	public List<Jobs> getJob() {
		return job;
	}
	public void setJob(List<Jobs> job) {
		this.job = job;
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