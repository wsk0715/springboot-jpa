package com.example.springboot_jpa.exception.handler;

import com.example.springboot_jpa.exception.handler.response.ErrorResponse;
import com.example.springboot_jpa.exception.type.NotFoundException;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
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


	/**
	 * 400 Bad Request
	 * 사용자 정의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(SpringbootJpaException.class)
	public ResponseEntity<ErrorResponse> handleSpringbootJpaException(SpringbootJpaException e) {
		return logWarnAndCreateResponse(e, HttpStatus.BAD_REQUEST);  //
	}

	/**
	 * 404 Not Found
	 * 요청한 콘텐츠를 찾지 못했을 경우의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleContentNotFoundException(NotFoundException e) {
		return logWarnAndCreateResponse(e, HttpStatus.NOT_FOUND);
	}

	/**
	 * 500 Internal Server Error
	 * 예외 처리를 하지 못한 나머지 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error("[Unknown Exception] {}: {}", e.getClass().getName(), e.getMessage());
		logErrorStackTrace(e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
							 .body(ErrorResponse.of("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

	private void logErrorStackTrace(Exception e) {
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement traceElement : trace) {
			log.error("\tat " + traceElement);
		}
	}

	private ResponseEntity<ErrorResponse> logWarnAndCreateResponse(SpringbootJpaException e,
																   HttpStatus status) {
		log.warn("[{} Exception] {}: {}", appName, e.getClass().getName(), e.getMessage());
		return ResponseEntity.status(status) // 404 Not Found
							 .body(ErrorResponse.of(e.getMessage(), status.value()));
	}

}
