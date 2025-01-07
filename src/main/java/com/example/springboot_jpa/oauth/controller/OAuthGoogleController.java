package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.AuthResponse;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.service.OAuthGoogleService;
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

	private final String authUrl = AUTHORIZATION_URL
								   + "?client_id=" + CLIENT_ID
								   + "&redirect_uri=" + REDIRECT_URI
								   + "&scope=" + SCOPE
								   + "&response_type=code";

	private final OAuthGoogleService OAuthGoogleService;

	private final AuthService authService;


	@GetMapping("")
	public ResponseEntity<AuthResponse> authentication() {
		return ResponseEntity.ok(new AuthResponse(authUrl));
	}

	@GetMapping("/callback")
	public ResponseEntity<Credential> callback(@RequestParam("code") String code,
											   HttpServletResponse response) {
		OAuthResult result = OAuthGoogleService.init(code);
		Credential credential = authService.setCredential(response, result.token());

		return ResponseEntity.ok(credential);
	}

}
