package com.example.springboot_jpa.auth.controller;

import com.example.springboot_jpa.common.credential.manager.CredentialManager;
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

	private final CredentialManager cookieCredentialManager;


	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		cookieCredentialManager.expireCredential(response);
		return ResponseEntity.ok().build();
	}

}
