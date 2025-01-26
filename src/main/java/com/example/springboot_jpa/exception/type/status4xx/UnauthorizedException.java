package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class UnauthorizedException extends SpringbootJpaException {

	public UnauthorizedException() {
		super("해당 요청을 수행하기 위해 인증이 필요합니다.");
	}

	public UnauthorizedException(final String message) {
		super(message);
	}

	public UnauthorizedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(final Throwable cause) {
		super("해당 요청을 수행하기 위해 인증이 필요합니다.", cause);
	}

}
