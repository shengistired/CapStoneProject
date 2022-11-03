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

public class StorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868661524970162604L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
