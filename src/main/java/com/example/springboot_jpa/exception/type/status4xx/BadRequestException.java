package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class BadRequestException extends SpringbootJpaException {

	public BadRequestException() {
		super("올바르지 않은 요청입니다.");
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(Throwable cause) {
		super("올바르지 않은 요청입니다.", cause);
	}

}
