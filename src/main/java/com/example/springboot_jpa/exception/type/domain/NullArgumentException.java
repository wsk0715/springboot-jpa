package com.example.springboot_jpa.exception.type.domain;

public class NullArgumentException extends Exception {

	private static final String DEFAULT_MESSAGE = "인자로 전달받은 값이 비어있습니다.";

	public NullArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	public NullArgumentException(String message) {
		super(message);
	}

	public NullArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullArgumentException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
