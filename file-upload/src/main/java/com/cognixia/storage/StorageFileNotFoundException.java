/*
 * 
 * 
 * 
 * Done By: Izdihar
 * 
 * 
 * 
 */

package com.cognixia.storage;

public class StorageFileNotFoundException extends StorageException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514545173074615427L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
