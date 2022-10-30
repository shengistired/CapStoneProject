package com.cognixia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.model.Login;
import com.cognixia.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@GetMapping
	public ResponseEntity<List<Login>> getLogin()
	{
		return ResponseEntity.ok(loginService.getLogin());
	}
	
	@PostMapping
	public ResponseEntity<Login> addEmployee(@RequestBody Login login)
	{
		Login createdLogin = loginService.addLogin(login);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdLogin.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Login> getLoginById(@PathVariable int id)
	{
		Login login = loginService.getLoginById(id);
		if(login == null)
			return ResponseEntity.notFound().build(); 
		else
			return ResponseEntity.ok(login);
	}
}
