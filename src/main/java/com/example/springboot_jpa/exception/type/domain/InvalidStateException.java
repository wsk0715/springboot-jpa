package com.example.springboot_jpa.exception.type.domain;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class InvalidStateException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "대상의 상태가 올바르지 않습니다.";

	public InvalidStateException() {
		super(DEFAULT_MESSAGE);
	}

	public InvalidStateException(String message) {
		super(message);
	}

	public InvalidStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidStateException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
