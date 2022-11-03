/*
 * 
 * 
 * 
 * Done By: Odelia
 * 
 * 
 * 
 */

package com.cognixia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.model.Login;
import com.cognixia.model.UserInfo;
import com.cognixia.repository.LoginRepository;
import com.cognixia.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	UserRepository userRepository;

	
	public List<Login> getLogin() {
		return loginRepository.findAll();
	}
	
	public void addLogin(Login login) {
		loginRepository.save(login);
	}
	
	public UserInfo getUser(UserInfo user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

}
