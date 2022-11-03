/*
 * 
 * 
 * 
 * Done By: Yong Sheng
 * 
 * 
 * 
 */

package com.cognixia.batch;

import java.io.File;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.cognixia.model.FileDetails;
import com.cognixia.service.StorageService;

public class FileItemProcessor implements ItemProcessor<FileDetails, FileDetails>{

	private final String dirString = "upload-dir";

	@Autowired
	StorageService storageService;

	@Override
	public FileDetails process(FileDetails item) throws Exception {
		File outputFile = new File(dirString + File.separator +  item.getFileName());
		outputFile.delete();
		storageService.deleteFiles(item.getFileName());
		return item;
	}

}
