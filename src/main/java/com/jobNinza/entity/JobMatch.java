package com.jobNinza.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.MatchingStrategy;

import com.jobNinza.enums.MatchRecommendation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class JobMatch {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//	@ManyToOne
//    Jobs job;

	@ManyToOne
    Profile profile;

    BigDecimal matchScore;

    BigDecimal roleScore;
    BigDecimal experienceScore;
    BigDecimal skillScore;
    BigDecimal locationScore;
    BigDecimal companyScore;

    String matchedSkills;
    String missingSkills;

    MatchRecommendation recommendation;

    MatchingStrategy matchingStrategy;

    BigDecimal aiScore;

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
	public BigDecimal getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(BigDecimal matchScore) {
		this.matchScore = matchScore;
	}
	public BigDecimal getRoleScore() {
		return roleScore;
	}
	public void setRoleScore(BigDecimal roleScore) {
		this.roleScore = roleScore;
	}
	public BigDecimal getExperienceScore() {
		return experienceScore;
	}
	public void setExperienceScore(BigDecimal experienceScore) {
		this.experienceScore = experienceScore;
	}
	public BigDecimal getSkillScore() {
		return skillScore;
	}
	public void setSkillScore(BigDecimal skillScore) {
		this.skillScore = skillScore;
	}
	public BigDecimal getLocationScore() {
		return locationScore;
	}
	public void setLocationScore(BigDecimal locationScore) {
		this.locationScore = locationScore;
	}
	public BigDecimal getCompanyScore() {
		return companyScore;
	}
	public void setCompanyScore(BigDecimal companyScore) {
		this.companyScore = companyScore;
	}
	public String getMatchedSkills() {
		return matchedSkills;
	}
	public void setMatchedSkills(String matchedSkills) {
		this.matchedSkills = matchedSkills;
	}
	public String getMissingSkills() {
		return missingSkills;
	}
	public void setMissingSkills(String missingSkills) {
		this.missingSkills = missingSkills;
	}
	public MatchRecommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(MatchRecommendation recommendation) {
		this.recommendation = recommendation;
	}
	public MatchingStrategy getMatchingStrategy() {
		return matchingStrategy;
	}
	public void setMatchingStrategy(MatchingStrategy matchingStrategy) {
		this.matchingStrategy = matchingStrategy;
	}
	public BigDecimal getAiScore() {
		return aiScore;
	}
	public void setAiScore(BigDecimal aiScore) {
		this.aiScore = aiScore;
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
