package com.jobNinza.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class JobSkill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@ManyToOne(fetch=FetchType.LAZY)
    private Jobs job;
	@ManyToOne(fetch=FetchType.LAZY)
    private Skill skill;
	boolean required;
	BigDecimal minimumExperience;

	public void setJob(Jobs job) {
		this.job = job;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public BigDecimal getMinimumExperience() {
		return minimumExperience;
	}

	public void setMinimumExperience(BigDecimal minimumExperience) {
		this.minimumExperience = minimumExperience;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Jobs getJob() {
		return job;
	}

//    SkillImportance skillImportance;

}
