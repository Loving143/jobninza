package com.jobNinza.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class JobPreference {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
    Profile profile;

    Integer minExperience;
    Integer maxExperience;

    BigDecimal minimumSalary;
    String currency;

    boolean remotePreferred;
    boolean willingToRelocate;

    boolean autoApplyEnabled;

    LocalDateTime createdAt;
	LocalDateTime updatedAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Integer getMinExperience() {
		return minExperience;
	}
	public void setMinExperience(Integer minExperience) {
		this.minExperience = minExperience;
	}
	public Integer getMaxExperience() {
		return maxExperience;
	}
	public void setMaxExperience(Integer maxExperience) {
		this.maxExperience = maxExperience;
	}
	public BigDecimal getMinimumSalary() {
		return minimumSalary;
	}
	public void setMinimumSalary(BigDecimal minimumSalary) {
		this.minimumSalary = minimumSalary;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public boolean isRemotePreferred() {
		return remotePreferred;
	}
	public void setRemotePreferred(boolean remotePreferred) {
		this.remotePreferred = remotePreferred;
	}
	public boolean isWillingToRelocate() {
		return willingToRelocate;
	}
	public void setWillingToRelocate(boolean willingToRelocate) {
		this.willingToRelocate = willingToRelocate;
	}
	public boolean isAutoApplyEnabled() {
		return autoApplyEnabled;
	}
	public void setAutoApplyEnabled(boolean autoApplyEnabled) {
		this.autoApplyEnabled = autoApplyEnabled;
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
