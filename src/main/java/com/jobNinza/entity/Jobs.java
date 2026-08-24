package com.jobNinza.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jobNinza.enums.EmploymentType;
import com.jobNinza.enums.RemoteType;

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
public class Jobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
    private Company company;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<JobSkill> jobSkills=new ArrayList<>();

    String externalJobId;

    String title;
    String description;

    String location;
    String country;

    @Enumerated(EnumType.STRING)
    RemoteType remoteType;

    @Enumerated(EnumType.STRING)
    EmploymentType employmentType;

    BigDecimal experienceMin;
    BigDecimal experienceMax;

    BigDecimal salaryMin;
    BigDecimal salaryMax;

    String currency;

    String jobUrl;

    String source;

    Instant postedAt;
    Instant scrapedAt;

    boolean active;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getExternalJobId() {
		return externalJobId;
	}
	public void setExternalJobId(String externalJobId) {
		this.externalJobId = externalJobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public RemoteType getRemoteType() {
		return remoteType;
	}
	public void setRemoteType(RemoteType remoteType) {
		this.remoteType = remoteType;
	}
	public EmploymentType getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}
	public BigDecimal getExperienceMin() {
		return experienceMin;
	}
	public void setExperienceMin(BigDecimal experienceMin) {
		this.experienceMin = experienceMin;
	}
	public BigDecimal getExperienceMax() {
		return experienceMax;
	}
	public void setExperienceMax(BigDecimal experienceMax) {
		this.experienceMax = experienceMax;
	}
	public BigDecimal getSalaryMin() {
		return salaryMin;
	}
	public void setSalaryMin(BigDecimal salaryMin) {
		this.salaryMin = salaryMin;
	}
	public BigDecimal getSalaryMax() {
		return salaryMax;
	}
	public void setSalaryMax(BigDecimal salaryMax) {
		this.salaryMax = salaryMax;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getJobUrl() {
		return jobUrl;
	}
	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Instant getPostedAt() {
		return postedAt;
	}
	public void setPostedAt(Instant postedAt) {
		this.postedAt = postedAt;
	}
	public Instant getScrapedAt() {
		return scrapedAt;
	}
	public void setScrapedAt(Instant scrapedAt) {
		this.scrapedAt = scrapedAt;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public List<JobSkill> getJobSkills() {
		return jobSkills;
	}
	public void setJobSkills(List<JobSkill> jobSkills) {
		this.jobSkills = jobSkills;
	}

}
