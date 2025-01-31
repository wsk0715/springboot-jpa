package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class NotFoundException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "해당 요청에 대한 항목을 찾을 수 없습니다.";

	public NotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
