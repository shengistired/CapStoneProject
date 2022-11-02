package com.cognixia.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/reconserver")
public class ReconController {
	
    @RequestMapping(path = "/send/", method = RequestMethod.POST)
	public void sendReconFile(@RequestParam("file") MultipartFile file) throws IOException {
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
		System.out.println("From recon server" +file.getOriginalFilename());
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
    	System.out.println("=======================");
		String url = "http://localhost:8084/purge/";
		File outputFile = new File(file.getOriginalFilename());
		try(FileOutputStream outputStream = new FileOutputStream(outputFile)){
			outputStream.write(file.getBytes());
		}
		org.springframework.core.io.Resource outputResource = new FileSystemResource(outputFile);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", outputResource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println("Response code: " + response.getStatusCode());
		
	}
}
