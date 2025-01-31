package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class UnauthorizedException extends SpringbootJpaException {

	private static final String DEFAULT_MESSAGE = "해당 요청을 수행하기 위해 인증이 필요합니다.";

	public UnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}

	public UnauthorizedException(final String message) {
		super(message);
	}

	public UnauthorizedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
