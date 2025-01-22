package com.example.springboot_jpa.auth.controller;

import com.example.springboot_jpa.auth.controller.request.AuthRequest;
import com.example.springboot_jpa.auth.controller.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "Authentication API")
public interface AuthControllerDocs {

	@Operation(summary = "회원가입", description = "ID/PW를 이용해 회원가입 요청을 보낸다.")
	@ApiResponses(value = {
			@ApiResponse(description = "회원가입 성공", responseCode = "200")
	})
	@PostMapping
	ResponseEntity<Void> signup(@RequestBody AuthRequest authRequest);

	@Operation(summary = "로그인", description = "ID/PW를 이용해 로그인 요청을 보낸다.")
	@ApiResponses(value = {
			@ApiResponse(description = "요청 성공", responseCode = "200"),
	})
	@PostMapping("/login")
	ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest,
							   HttpServletResponse response);

	@Operation(summary = "로그아웃", description = "로그아웃 요청을 보낸다.")
	@ApiResponses(value = {
			@ApiResponse(description = "로그아웃 성공", responseCode = "200")
	})
	ResponseEntity<Void> logout(HttpServletResponse response);

}
