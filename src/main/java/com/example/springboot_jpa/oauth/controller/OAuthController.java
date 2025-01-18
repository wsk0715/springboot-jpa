package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.AuthResponse;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.properties.OAuthGoogleProperties;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/{provider}")
@EnableConfigurationProperties({OAuthGoogleProperties.class, OAuthKakaoProperties.class})
public class OAuthController implements OAuthControllerDocs {

	private final OAuthService oAuthService;

	private final AuthService authService;


	@Override
	@GetMapping("")
	public ResponseEntity<AuthResponse> getUrl(@PathVariable String provider) {
		return ResponseEntity.ok(new AuthResponse(oAuthService.getUrl(provider)));
	}
	
	@Override
	@GetMapping("/callback")
	public ResponseEntity<Credential> callback(@PathVariable String provider,
											   @RequestParam("code") String code,
											   HttpServletResponse response) {
		OAuthResult result = oAuthService.init(provider, code);
		Credential credential = authService.setCredential(response, result.token());

		return ResponseEntity.ok(credential);
	}

}
