package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
	
}
