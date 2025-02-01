package com.example.springboot_jpa.exception.type.domain;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class ArgumentFormatException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "인자로 전달받은 값의 형식이 올바르지 않습니다.";

	public ArgumentFormatException() {
		super(DEFAULT_MESSAGE);
	}

	public ArgumentFormatException(String message) {
		super(message);
	}

	public ArgumentFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentFormatException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
