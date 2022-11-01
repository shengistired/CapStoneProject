package com.cognixia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.cognixia.service.FileService;
import com.cognixia.service.StorageProperties;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FileServiceUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServiceUploadApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

}
