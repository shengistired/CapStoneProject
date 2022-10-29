package com.cognixia.service;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cognixia.exception.FileNotFoundException;
import com.cognixia.exception.FileStorageException;
import com.cognixia.model.DatabaseFile;
import com.cognixia.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {

	 @Autowired
	    private DatabaseFileRepository dbFileRepository;

	    public DatabaseFile storeFile(MultipartFile file) {
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequenth " + fileName);        }

	            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

	            return dbFileRepository.save(dbFile);

	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }

	    public DatabaseFile getFile(String fileId) {
	        return dbFileRepository.findById(fileId)
	                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
	    }
	}