package com.jobNinza.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobSkill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

    Job job;

    Skill skill;

    boolean required;

    BigDecimal minimumExperience;

	public void setJob(Job job) {
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

//    SkillImportance skillImportance;

}
