package com.cognixia.model;

//• File type
//• File name
//• File size
//• File ID
//• Uploaded by
//• Uploaded timestamp

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table (name = "files")
public class DatabaseFile {
	
	 @Id
	 @GeneratedValue(generator = "uuid")
	 @GenericGenerator(name = "uuid", strategy = "uuid2")
	 private String id;

	 private String fileName;

	 private String fileType;
	 
	 private String fileSize;
	 
	 private String uploadedBy;
	 
	 private Timestamp timeStamp;
	 
	 @Lob
	 private byte [] data;
	 
	 


	public DatabaseFile(String id, String fileName, String fileType, String fileSize, String uploadedBy,
			Timestamp timeStamp, byte[] data) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.uploadedBy = uploadedBy;
		this.timeStamp = timeStamp;
		this.data = data;
	}


	public DatabaseFile(String fileName2, String contentType, byte[] bytes) {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	 
}
