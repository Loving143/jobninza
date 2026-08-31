
package com.jobNinza.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import com.jobNinza.enums.EmploymentType;
import com.jobNinza.enums.RemoteType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "external_job_id", length = 255)
    private String externalJobId;

    @Column(name = "requisition_id", length = 255)
    private String requisitionId;

    @Column(name = "internal_job_id")
    private Long internalJobId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "department", length = 255)
    private String department;

    @Column(name = "language", length = 255)
    private String language;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "postal_code", length = 255)
    private String postalCode;

    // Job attributes

    @Enumerated(EnumType.STRING)
    @Column(name = "remote_type", length = 50)
    private RemoteType remoteType;

    @Column(name = "workplace_type", length = 255)
    private String workplaceType;

    @Column(name = "employment_type_string", length = 255)
    private String employmentTypeString;

    @Column(name = "experience_min", precision = 38, scale = 2)
    private BigDecimal experienceMin;

    @Column(name = "experience_max", precision = 38, scale = 2)
    private BigDecimal experienceMax;

    @Column(name = "experience_level", length = 255)
    private String experienceLevel;

    // Compensation

    @Column(name = "salary_min", precision = 38, scale = 2)
    private BigDecimal salaryMin;

    @Column(name = "salary_max", precision = 38, scale = 2)
    private BigDecimal salaryMax;

    @Column(name = "currency", length = 255)
    private String currency;

    @Column(name = "salary_period", length = 255)
    private String salaryPeriod;

    @Column(name = "job_url", length = 1000)
    private String jobUrl;

    @Column(name = "application_url", length = 1000)
    private String applicationUrl;

    @Column(name = "absolute_url", length = 1000)
    private String absoluteUrl;

    // Dates

    @Column(name = "posted_at")
    private Instant postedAt;

    @Column(name = "first_published", length = 255)
    private String firstPublished;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "application_deadline", length = 255)
    private String applicationDeadline;

    @Column(name = "closed_at")
    private Instant closedAt;

    // Metadata

    @Column(name = "source", length = 255)
    private String source;

    @Column(name = "source_ats_type", length = 255)
    private String sourceAtsType;

    @Column(name = "raw_data", columnDefinition = "TEXT")
    private String rawData;

    @Column(name = "scraped_at")
    private Instant scrapedAt;

    // Status

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "is_remote")
    private Boolean isRemote;

    // Timestamps

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Long getInternalJobId() {
        return internalJobId;
    }

    public void setInternalJobId(Long internalJobId) {
        this.internalJobId = internalJobId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public RemoteType getRemoteType() {
        return remoteType;
    }

    public void setRemoteType(RemoteType remoteType) {
        this.remoteType = remoteType;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public String getEmploymentTypeString() {
        return employmentTypeString;
    }

    public void setEmploymentTypeString(String employmentTypeString) {
        this.employmentTypeString = employmentTypeString;
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

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
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

    public String getSalaryPeriod() {
        return salaryPeriod;
    }

    public void setSalaryPeriod(String salaryPeriod) {
        this.salaryPeriod = salaryPeriod;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public String getFirstPublished() {
        return firstPublished;
    }

    public void setFirstPublished(String firstPublished) {
        this.firstPublished = firstPublished;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceAtsType() {
        return sourceAtsType;
    }

    public void setSourceAtsType(String sourceAtsType) {
        this.sourceAtsType = sourceAtsType;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
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

    public Boolean getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(Boolean isRemote) {
        this.isRemote = isRemote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}