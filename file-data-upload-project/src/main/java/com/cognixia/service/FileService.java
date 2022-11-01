package com.cognixia.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognixia.model.File;

public interface FileService {
	
	void init();

	File saveFile(MultipartFile file) throws Exception;

	File getFile(String fileId) throws Exception;

	public Stream<Path> loadAll();
	
	
	static Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}



}
