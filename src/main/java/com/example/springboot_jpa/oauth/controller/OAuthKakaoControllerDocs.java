package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;


@Tag(name = "Kakao Oauth", description = "Kakao Oauth 인증 관련 API")
public interface OAuthKakaoControllerDocs {

	@Operation(summary = "카카오 인증", description = "사용자가 카카오 계정을 통해 인증하도록 한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "리디렉션 요청", responseCode = "200")
	})
	ResponseEntity<BaseResponse> authentication();

	@Operation(summary = "사용자 확인", description = "액세스 토큰을 이용해 사용자를 식별한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "최초 로그인", responseCode = "200"),
			@ApiResponse(description = "사용자 확인 성공", responseCode = "200")
	})
	ResponseEntity<BaseResponse> callback(String code, HttpServletResponse response);

}
