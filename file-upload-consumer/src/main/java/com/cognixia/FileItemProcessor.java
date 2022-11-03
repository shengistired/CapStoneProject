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

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.batch.item.ItemProcessor;

import com.cognixia.model.FileDetails;

public class FileItemProcessor implements ItemProcessor<FileDetails, FileDetails>{

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
