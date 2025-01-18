package com.example.springboot_jpa.common.exception.handler;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.common.exception.handler.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@Value("${spring.application.name}")
	private String appName;

	@ExceptionHandler(SpringbootJpaException.class)
	public ResponseEntity<ErrorResponse> handleSpringbootJpaException(SpringbootJpaException e) {
		log.warn("[{} Exception] {}: {}", appName, e.getClass().getName(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Bad Request
							 .body(ErrorResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error("[Unknown Exception] {}: {}", e.getClass().getName(), e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error 응답
							 .body(ErrorResponse.of("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

}
