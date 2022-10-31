package com.cognixia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.model.Person;
import com.cognixia.model.UserInfo;
import com.cognixia.repository.PersonRepository;
import com.cognixia.repository.UserRepository;

@Service
public class WebService {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public void addPerson(Person person) {
		personRepository.save(person);
	}
	
	public UserInfo getUser(UserInfo user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
}
