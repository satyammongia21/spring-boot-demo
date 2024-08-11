package com.tcs.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5541171236167467121L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
