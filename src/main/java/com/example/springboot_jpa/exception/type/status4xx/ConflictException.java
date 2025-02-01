package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class ConflictException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "해당 자원이 이미 존재합니다.";

	public ConflictException() {
		super(DEFAULT_MESSAGE);
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
