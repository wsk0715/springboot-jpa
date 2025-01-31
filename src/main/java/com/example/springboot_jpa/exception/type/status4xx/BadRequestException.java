package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class BadRequestException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "올바르지 않은 요청입니다.";

	public BadRequestException() {
		super(DEFAULT_MESSAGE);
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
