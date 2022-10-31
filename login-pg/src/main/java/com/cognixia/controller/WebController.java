package com.cognixia.controller;


import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cognixia.model.Login;
import com.cognixia.service.LoginService;

@Controller
public class WebController {
	
	@Autowired
	LoginService loginService;
	
	@GetMapping("/login")
	public String showLogin(Login login) throws IOException {

		return "login";
	}
	
//	@PostMapping("/login")
//	public String printLogin(Model model) {
//
//		return "redirect:/form";
//	}
	
	@PostMapping("/login")
	public String addLogin(Login login) {
		
		loginService.addLogin(login);
		return "fileUpload";
	}

}
