package com.jobNinza.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	Users user;

    String fileName;
    String filePath;
    String fileType;
    Long fileSize;

    Integer version;

    boolean primaryResume;

    boolean parsed;
    LocalDateTime parsedAt;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
