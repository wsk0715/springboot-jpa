package com.example.springboot_jpa.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Authentication API")
public interface AuthControllerDocs {

	@Operation(summary = "로그아웃", description = "사용자를 로그아웃 시킨다.")
	@ApiResponses(value = {
			@ApiResponse(description = "로그아웃 성공", responseCode = "200")
	})
	ResponseEntity<Void> logout(HttpServletResponse response);

}
