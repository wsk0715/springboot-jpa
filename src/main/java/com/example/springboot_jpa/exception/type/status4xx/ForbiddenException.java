package com.example.springboot_jpa.exception.type.status4xx;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;

public class ForbiddenException extends SpringbootJpaException {

	public ForbiddenException() {
		super("해당 요청에 대한 권한이 충분하지 않습니다.");
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(Throwable cause) {
		super("해당 요청에 대한 권한이 충분하지 않습니다.", cause);
	}

}
