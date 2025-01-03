package com.example.springboot_jpa.auth.controller;

import com.example.springboot_jpa.common.response.BaseResponse;
import com.example.springboot_jpa.common.util.JwtCookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

	private final JwtCookieUtil jwtCookieUtil;


	@GetMapping("/logout")
	public ResponseEntity<BaseResponse> logout(HttpServletResponse response) {
		jwtCookieUtil.expireJwtFromToken(response);

		BaseResponse res = BaseResponse.ok()
									   .title("로그아웃 성공")
									   .description("로그아웃에 성공했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

}
