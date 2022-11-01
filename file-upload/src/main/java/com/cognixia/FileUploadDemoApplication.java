package com.cognixia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.cognixia.service.StorageService;
import com.cognixia.storage.StorageProperties;

@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients
@EnableConfigurationProperties(StorageProperties.class)
public class FileUploadDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadDemoApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
	

}
