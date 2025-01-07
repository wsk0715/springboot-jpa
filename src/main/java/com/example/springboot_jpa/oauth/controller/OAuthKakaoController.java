package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.AuthResponse;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.oauth.service.OAuthKakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/kakao")
@EnableConfigurationProperties(OAuthKakaoProperties.class)
public class OAuthKakaoController implements OAuthKakaoControllerDocs {

	private final OAuthKakaoProperties oAuthKakaoProperties;

	private final OAuthKakaoService OAuthKakaoService;

	private final AuthService authService;


	@GetMapping
	public ResponseEntity<AuthResponse> authentication() {
		return ResponseEntity.ok(new AuthResponse(oAuthKakaoProperties.getUrl()));
	}

	@GetMapping("/callback")
	public ResponseEntity<Credential> callback(@RequestParam("code") String code,
											   HttpServletResponse response) {
		OAuthResult result = OAuthKakaoService.init(code);
		Credential credential = authService.setCredential(response, result.token());

		return ResponseEntity.ok(credential);
	}

}
