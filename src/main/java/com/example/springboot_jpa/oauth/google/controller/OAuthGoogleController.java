package com.example.springboot_jpa.oauth.google.controller;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.google.service.OAuthGoogleService;
import com.example.springboot_jpa.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/google")
public class OAuthGoogleController implements OAuthGoogleControllerDocs {

	@Value("${server.baseUrl}")
	private String BASE_URL;

	@Value("${security.oauth.google.authUrl}")
	private String AUTHORIZATION_URL;

	@Value("${security.oauth.google.clientId}")
	private String CLIENT_ID;

	@Value("${security.oauth.google.redirectUri}")
	private String REDIRECT_URI;

	private final String SCOPE = "openid%20profile%20email";

	private final OAuthGoogleService OAuthGoogleService;

	private final AuthService authService;


	@GetMapping("")
	public ResponseEntity<BaseResponse> authentication() {
		String url = AUTHORIZATION_URL
					 + "?client_id=" + CLIENT_ID
					 + "&redirect_uri=" + REDIRECT_URI
					 + "&scope=" + SCOPE
					 + "&response_type=code";

		BaseResponse res = BaseResponse.redirect()
									   .title("구글 인증 요청")
									   .description("data에 담긴 URL로 이동해 주세요.")
									   .data(url)
									   .build();
		return ResponseEntity.ok(res);
	}

	@GetMapping("/callback")
	public ResponseEntity<BaseResponse> callback(@RequestParam("code") String code,
												 HttpServletResponse response) {
		OAuthResult result = OAuthGoogleService.init(code);

		authService.addJwtToCookie(response, result.token());

		if (result.initialLogin()) {
			String url = BASE_URL + "/auth";

			BaseResponse res = BaseResponse.ok()
										   .title("최초 로그인")
										   .description("data에 담긴 URL에 nickname을 POST 요청해주세요.")
										   .data(url)
										   .build();
			return ResponseEntity.ok(res);
		}

		BaseResponse res = BaseResponse.ok()
									   .title("로그인 성공")
									   .description("토큰이 발급되었습니다.")
									   .data(result.token())
									   .build();
		return ResponseEntity.ok(res);
	}

}
