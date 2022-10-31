package com.cognixia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognixia.model.Person;
import com.cognixia.model.UserInfo;
import com.cognixia.service.WebService;

@Controller
public class WebController {

	@Autowired
	WebService webService;
	
	@GetMapping("/")
	public String showLoginForm(UserInfo userInfo) {
		return "login";
	}
	@PostMapping("/")
	public String checkLoginInfo(@Valid UserInfo userInfo, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors() || webService.getUser(userInfo) == null) {
			return "login";
		}
		
		return "personform";
	}
	
//	@GetMapping("/person")
//	public String showPersonForm(Person person) {
//		return "personform";
//	}
	
	@PostMapping("/person")
	public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "personform";
		}
		webService.addPerson(person);
		return "results";
	}

}
