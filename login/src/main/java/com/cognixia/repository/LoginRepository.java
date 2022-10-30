package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{
	
}
