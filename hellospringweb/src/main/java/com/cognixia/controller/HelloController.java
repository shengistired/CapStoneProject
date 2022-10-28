package com.cognixia.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.model.HelloWeb;

@RestController
public class HelloController { 
	
	@Value("${server.port}")
	private int port;
	@GetMapping("hello")//map with http method get
	public String sayHello()
	{
		return "Hello World! from " + this.port;
	}
	
	@GetMapping("helloobj")
	public HelloWeb sayHelloObject() {
		HelloWeb hw = new HelloWeb();
		hw.setMessage("Hello from object");
		return hw;
	}
	
	@GetMapping("hellobyname")
	public String sayHelloByName(@RequestParam String name) {
		return "Hello " + name + "!";
	}
	
	@GetMapping("hellobynamepath/{name}")
	public String sayHelloByNamePath(@PathVariable String name) {
		return "Hello " + name + "!";
	}
	
	@GetMapping("hellojsonbyparam")
	public HelloWeb helloJson(@RequestParam String name) {
		HelloWeb hw = new HelloWeb();
		hw.setName(name);
		hw.setMessage("HELLO JSON BY PARAM");
		return hw;
	}
	@GetMapping("hellojsonbypath/{name}")
	public HelloWeb helloJsonByPath(@PathVariable String name) {
		HelloWeb hw = new HelloWeb();
		hw.setName(name);
		hw.setMessage("HELLO JSON BY PATH");
		return hw;
	}
	@PostMapping("helloobj")
	public HelloWeb addGreeting(@RequestBody HelloWeb hw) {
		hw.setName(hw.getName().toUpperCase());
		return hw;
		
	}

}
