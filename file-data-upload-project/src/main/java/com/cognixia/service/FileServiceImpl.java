package com.cognixia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cognixia.common.StorageException;
import com.cognixia.model.File;
import com.cognixia.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	private final Path rootLocation;

	
//	public FileServiceImpl(FileRepository fileRepository) {
//		this.fileRepository = fileRepository;
//	}
	
	@Autowired
	public FileServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}


	@Override
	public File saveFile(MultipartFile file) throws Exception{
		String fileName= StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new Exception("Filename Error");
			}
			File files = new File(fileName,file.getContentType(),file.getBytes());
			return fileRepository.save(files);
		}catch(Exception e) {
			throw new Exception("Could not save file: " + fileName);
		}
	}


	@Override
	public File getFile(String fileId) throws Exception {
		// TODO Auto-generated method stub
		return fileRepository.findById(fileId).orElseThrow(() -> new Exception("File not found with Id: " + fileId));
	}


	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			
			throw new StorageException("File Error");
		}
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


//	@Override
//	public Resource loadAsResource(String filename) {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
