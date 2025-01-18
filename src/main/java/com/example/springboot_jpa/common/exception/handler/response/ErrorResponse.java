package com.example.springboot_jpa.common.exception.handler.response;

public record ErrorResponse(
		String message,
		int statusCode
) {

	public static ErrorResponse of(String message, int code) {
		return new ErrorResponse(message, code);
	}

}
