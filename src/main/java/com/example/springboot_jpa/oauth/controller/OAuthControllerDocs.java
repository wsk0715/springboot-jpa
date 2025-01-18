package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.AuthResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "OAuth", description = "OAuth 인증 관련 API")
public interface OAuthControllerDocs {

	@Operation(summary = "인증 URL 요청", description = "OAuth 인증 URL을 제공한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "성공", responseCode = "200")
	})
	ResponseEntity<AuthResponse> getUrl(@PathVariable String provider);

	@Hidden
	@Operation(summary = "사용자 확인", description = "OAuth 제공자로부터 받은 일회성 코드를 사용해 토큰을 통해 사용자를 식별한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "사용자 확인 성공", responseCode = "200")
	})
	ResponseEntity<Credential> callback(@PathVariable String provider,
										@RequestParam("code") String code,
										HttpServletResponse response);

}
