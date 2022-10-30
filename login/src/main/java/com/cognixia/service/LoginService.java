package com.cognixia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.model.Login;
import com.cognixia.repository.LoginRepository;

@Service
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	
	public List<Login> getLogin() {
		return loginRepository.findAll();
	}
	
	public Login addLogin(Login login) {
		return loginRepository.save(login);
	}
	
	public Login getLoginById(int id) {
		
		return loginRepository.findById(id).orElseThrow();
	}
}
