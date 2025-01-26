package com.example.springboot_jpa.exception.handler;

import com.example.springboot_jpa.exception.handler.response.ErrorResponse;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import com.example.springboot_jpa.exception.type.status4xx.BadRequestException;
import com.example.springboot_jpa.exception.type.status4xx.ForbiddenException;
import com.example.springboot_jpa.exception.type.status4xx.NotFoundException;
import com.example.springboot_jpa.exception.type.status4xx.UnauthorizedException;
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
	 * 400 Bad Request<br>
	 * 사용자 정의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e) {
		return createErrorResponse(e, HttpStatus.BAD_REQUEST);  //
	}

	/**
	 * 401 Unauthorized<br>
	 * 인증되지 않은 사용자가 인증이 필요한 항목에 접근한 경우의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e) {
		return createErrorResponse(e, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 403 Forbidden<br>
	 * 요청이 권한을 벗어났을 경우의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e) {
		return createErrorResponse(e, HttpStatus.FORBIDDEN);
	}

	/**
	 * 404 Not Found<br>
	 * 요청한 콘텐츠를 찾지 못했을 경우의 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
		return createErrorResponse(e, HttpStatus.NOT_FOUND);
	}

	/**
	 * 500 Internal Server Error<br>
	 * 예외 처리를 했지만, 임시로 분류된 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(SpringbootJpaException.class)
	public ResponseEntity<ErrorResponse> handleSpringbootJpaException(SpringbootJpaException e) {
		return createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 500 Internal Server Error<br>
	 * 예외 처리를 하지 못한 나머지 예외를 처리하는 핸들러 메소드
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		return createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	private ResponseEntity<ErrorResponse> createErrorResponse(Exception e, HttpStatus status) {
		logMessage(e, status);
		return ResponseEntity.status(status)
							 .body(ErrorResponse.of(e.getMessage(), status.value()));
	}

	private void logMessage(Exception e, HttpStatus status) {
		if (status.is4xxClientError()) {
			log.info("[{}] {}: {}",
					 status.getReasonPhrase(),
					 e.getClass().getSimpleName(),
					 e.getMessage());
			return;
		}
		if (status.is5xxServerError()) {
			log.error("[{} Exception] {}: {}",
					  e instanceof SpringbootJpaException ? appName : "Unknown",
					  e.getClass().getSimpleName(),
					  e.getMessage(),
					  e);
		}
	}

}
