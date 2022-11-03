/*
 * 
 * 
 * 
 * Done By: Izdihar
 * 
 * 
 * 
 */
package com.cognixia.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class FileDetails implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -5797887707475706370L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	 private String fileName;

	 private String fileType;
	 
	 private long fileSize;
	 
	 private String uploadedBy;
	 
	 private Timestamp timeStamp;
	 
	 public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long l) {
		this.fileSize = l;
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	private byte [] data;

	public FileDetails() {
		
	}
	public FileDetails(String id, String fileName, String fileType, long fileSize, String uploadedBy,
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
	 
	 
	
}
