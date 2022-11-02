package com.cognixia.batch;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.batch.item.ItemProcessor;

import com.cognixia.model.FileDetails;

public class FileItemProcessor implements ItemProcessor<FileDetails, FileDetails>{

	private final String dirString = "upload-dir";

	@Override
	public FileDetails process(FileDetails item) throws Exception {
		File outputFile = new File(dirString + File.separator +  item.getFileName());
		outputFile.delete();
		return null;
	}

}
