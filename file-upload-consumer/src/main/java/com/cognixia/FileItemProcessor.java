package com.cognixia;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.print.DocFlavor.BYTE_ARRAY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.FileSystemResource;

import com.cognixia.model.FileDetails;

public class FileItemProcessor implements ItemProcessor<FileDetails, FileDetails>{

	private static final Logger log = LoggerFactory.getLogger(FileItemProcessor.class);

	private final String dirString = "upload-dir";
	@Override
		public FileDetails process(FileDetails item) throws Exception {
		new File("upload-dir").mkdir();


		// TODO Auto-generated method stub
		File outputFile = new File(dirString + File.separator +  item.getFileName());
		try(FileOutputStream outputStream = new FileOutputStream(outputFile)){
			outputStream.write(item.getData());
		}
		return item;
	}

}
