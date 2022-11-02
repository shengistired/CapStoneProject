package com.cognixia.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.stream.Stream;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cognixia.controller.FileUploadController;
import com.cognixia.model.FileDetails;
import com.cognixia.repository.FileStorageRepository;
import com.cognixia.storage.StorageException;
import com.cognixia.storage.StorageFileNotFoundException;
import com.cognixia.storage.StorageProperties;
import com.ctc.wstx.shaded.msv_core.util.StringPair;

@Service
public class FileSystemStorageService implements StorageService{
	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Autowired
	FileStorageRepository fileStorageRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path checkFile = Paths.get(this.rootLocation + File.separator  + file.getOriginalFilename());
			boolean exist = false;
			if(Files.exists(checkFile)) {
				exist = true;
			}

			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);

				if(file!= null && !exist) {
					FileDetails fileDetails = new FileDetails();
					
					fileDetails.setFileName(file.getOriginalFilename());
					fileDetails.setFileSize(file.getSize());
					fileDetails.setFileType(file.getContentType());
					fileDetails.setTimeStamp(new Timestamp(System.currentTimeMillis()));
					fileDetails.setUploadedBy(FileUploadController.usernameString);
					fileStorageRepository.save(fileDetails);
					fileDetails.setData(file.getBytes());
					rabbitTemplate.convertAndSend("fileupload", fileDetails);


				}
			}

		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void deleteFiles(String fileName) {
		fileStorageRepository.deleteAllByFileName(fileName);
		
//		for(File file: name) {
//			FileSystemUtils.deleteRecursively(file);
//		}
		// TODO Auto-generated method stub
		
	}
}
