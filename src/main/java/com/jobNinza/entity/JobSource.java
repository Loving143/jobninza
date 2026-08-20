package com.jobNinza.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class JobSource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;

	    String name;

//	    JobSourceType type;

	    String baseUrl;

	    boolean active;

	    Instant lastSuccessAt;
	    Instant lastFailureAt;

	    Integer failureCount;

	    LocalDateTime createdAt;
	    LocalDateTime updatedAt;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getBaseUrl() {
			return baseUrl;
		}
		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		public Instant getLastSuccessAt() {
			return lastSuccessAt;
		}
		public void setLastSuccessAt(Instant lastSuccessAt) {
			this.lastSuccessAt = lastSuccessAt;
		}
		public Instant getLastFailureAt() {
			return lastFailureAt;
		}
		public void setLastFailureAt(Instant lastFailureAt) {
			this.lastFailureAt = lastFailureAt;
		}
		public Integer getFailureCount() {
			return failureCount;
		}
		public void setFailureCount(Integer failureCount) {
			this.failureCount = failureCount;
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
