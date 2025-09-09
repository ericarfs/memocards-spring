package com.ericarfs.memocards.exceptions;

public class DuplicatedResourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicatedResourceException(String message) {
		super(message);
	}

}
