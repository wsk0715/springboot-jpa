package com.example.springboot_jpa.oauth.controller;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.credential.dto.Credential;
import com.example.springboot_jpa.oauth.controller.response.OAuthResponse;
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
	public ResponseEntity<OAuthResponse> getUrl(@PathVariable String provider) {
		return ResponseEntity.ok(new OAuthResponse(oAuthService.getUrl(provider)));
	}

	@Override
	@GetMapping("/callback")
	public ResponseEntity<Credential> callback(@PathVariable String provider,
											   @RequestParam("code") String code,
											   HttpServletResponse response) {
		Credential result = oAuthService.init(provider, code);
		Credential credential = authService.setCredential(response, result.jwt());

		return ResponseEntity.ok(credential);
	}

}
