package com.jobNinza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobNinza.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByActiveTrue();

}
