package com.example.springboot_jpa.exception.type.domain;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class ArgumentNullException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "인자로 전달받은 값이 비어있습니다.";

	public ArgumentNullException() {
		super(DEFAULT_MESSAGE);
	}

	public ArgumentNullException(String message) {
		super(message);
	}

	public ArgumentNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentNullException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
