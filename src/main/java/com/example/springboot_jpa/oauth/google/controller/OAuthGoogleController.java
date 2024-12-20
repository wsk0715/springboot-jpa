package com.example.springboot_jpa.oauth.google.controller;

import com.example.springboot_jpa.oauth.google.service.OAuthGoogleService;
import com.example.springboot_jpa.oauth.response.OAuthResponse;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.domain.Nickname;
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


	@GetMapping("")
	public ResponseEntity<BaseResponse> authentication() {
		String url = AUTHORIZATION_URL
					 + "?client_id=" + CLIENT_ID
					 + "&redirect_uri=" + REDIRECT_URI
					 + "&scope=" + SCOPE
					 + "&response_type=code";

		OAuthResponse res = OAuthResponse.redirect()
										 .title("구글 인증 요청")
										 .description("targetUrl로 이동해 주세요.")
										 .targetUrl(url)
										 .build();
		return ResponseEntity.ok(res);
	}

	@GetMapping("/callback")
	public ResponseEntity<BaseResponse> callback(@RequestParam("code") String code) {
		if (!OAuthGoogleService.init(code)) {
			String url = BASE_URL + "/auth";

			OAuthResponse res = OAuthResponse.ok()
											 .title("최초 로그인")
											 .description("targetUrl에 nickname을 POST 요청해주세요.")
											 .data(new Nickname("example01"))
											 .targetUrl(url)
											 .build();
			return ResponseEntity.ok(res);
		}

		OAuthResponse res = OAuthResponse.ok()
										 .title("로그인 성공")
										 .description("메인 페이지로 이동해주세요.")
										 .build();
		return ResponseEntity.ok(res);
	}

}
