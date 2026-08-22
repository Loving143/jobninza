package com.jobNinza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobNinza.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByEmail(String email);

}
