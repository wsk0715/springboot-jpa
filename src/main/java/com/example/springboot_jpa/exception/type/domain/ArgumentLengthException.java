package com.example.springboot_jpa.exception.type.domain;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class ArgumentLengthException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "인자로 전달받은 값의 길이가 잘못되었습니다.";

	public ArgumentLengthException() {
		super(DEFAULT_MESSAGE);
	}

	public ArgumentLengthException(String message) {
		super(message);
	}

	public ArgumentLengthException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentLengthException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
