package com.cognixia;

import java.io.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

	private final String reconFile = "reconFile.txt";
	@RequestMapping(value = "/getRecon", method = RequestMethod.GET)
	public File handleFileUpload() {
		
		return new File(reconFile);
	}
}
