/*
 * 
 * 
 * 
 * Done By: Yong Sheng
 * 
 * 
 * 
 */
package com.cognixia;


import java.io.IOException;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class SendReconFileListener extends JobExecutionListenerSupport {


	@Autowired
	public SendReconFileListener() {
		
	}

	public Resource outputResource = new FileSystemResource("reconFile.txt");

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		try {
			System.out.println(" GET LENGTH FROM CONSUMER" +outputResource.getFile().length());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(outputResource.getFile().length() != 0) {
				String url = "http://localhost:8082/reconserver/send/";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				RestTemplate restTemplate = new RestTemplate();
		        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		        body.add("file", outputResource);
		        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
		        System.out.println("Response code: " + response.getStatusCode());
		        outputResource.getFile().delete();

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}