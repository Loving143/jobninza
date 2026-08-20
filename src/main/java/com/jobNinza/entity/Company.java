package com.jobNinza.entity;

import java.time.LocalDateTime;

import com.jobNinza.enums.CompanyType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    String name;
    String normalizedName;

    String website;
    String careerUrl;

    CompanyType companyType;

//    CareerPlatform careerPlatform;

    boolean active;

    String description;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
