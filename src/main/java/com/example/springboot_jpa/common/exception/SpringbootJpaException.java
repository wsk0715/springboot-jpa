package com.example.springboot_jpa.common.exception;

public class SpringbootJpaException extends RuntimeException {

	public SpringbootJpaException() {
		super();
	}

	public SpringbootJpaException(String message) {
		super(message);
	}

	public SpringbootJpaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpringbootJpaException(Throwable cause) {
		super(cause);
	}

}
