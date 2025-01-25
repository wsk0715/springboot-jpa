package com.example.springboot_jpa.exception.handler.response;

/**
 * 클라이언트에 에러 정보를 전달하는 DTO
 */
public record ErrorResponse(
		String message,
		int statusCode
) {

	public static ErrorResponse of(String message, int code) {
		return new ErrorResponse(message, code);
	}

}
