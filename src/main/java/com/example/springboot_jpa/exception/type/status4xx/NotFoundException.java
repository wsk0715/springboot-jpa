package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class NotFoundException extends SpringbootJpaException {

	public NotFoundException() {
		super("항목을 찾을 수 없습니다.");
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super("항목을 찾을 수 없습니다.", cause);
	}

}
