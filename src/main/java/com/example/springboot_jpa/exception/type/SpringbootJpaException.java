package com.example.springboot_jpa.exception.type;

public class SpringbootJpaException extends RuntimeException {

	public SpringbootJpaException() {
		super("서버 내부 오류가 발생했습니다.");
	}

	public SpringbootJpaException(String message) {
		super(message);
	}

	public SpringbootJpaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpringbootJpaException(Throwable cause) {
		super("서버 내부 오류가 발생했습니다.", cause);
	}

}
