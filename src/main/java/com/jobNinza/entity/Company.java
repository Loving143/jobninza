package com.jobNinza.entity;

import com.jobNinza.enums.ATSType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Company {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(nullable = false, unique = true)
	    private String companyName;
	    
	    private String slug;
	    
	    private String ats;
	    
	    private boolean verified;
	    
	    private String applyHost;

	    @Column(nullable = false)
	    private boolean active = true;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}

		public String getAts() {
			return ats;
		}

		public void setAts(String ats) {
			this.ats = ats;
		}

		public boolean isVerified() {
			return verified;
		}

		public void setVerified(boolean verified) {
			this.verified = verified;
		}

		public String getApplyHost() {
			return applyHost;
		}

		public void setApplyHost(String applyHost) {
			this.applyHost = applyHost;
		}
	    
	
}
