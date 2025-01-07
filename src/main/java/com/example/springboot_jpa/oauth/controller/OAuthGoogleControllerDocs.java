package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Google Oauth", description = "Google OAuth 인증 관련 API")
public interface OAuthGoogleControllerDocs {

	@Operation(summary = "구글 인증", description = "사용자가 구글 계정을 통해 인증하도록 한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "리디렉션 요청", responseCode = "200")
	})
	ResponseEntity<AuthResponse> authentication();

	@Operation(summary = "사용자 확인", description = "액세스 토큰을 이용해 사용자를 식별한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "사용자 확인 성공", responseCode = "200")
	})
	ResponseEntity<Credential> callback(@RequestParam("code") String code,
										HttpServletResponse response);

}
