package com.example.springboot_jpa.auth.controller;

import com.example.springboot_jpa.auth.controller.request.AuthRequest;
import com.example.springboot_jpa.auth.controller.request.LoginRequest;
import com.example.springboot_jpa.auth.controller.response.AuthResponse;
import com.example.springboot_jpa.auth.domain.Auth;
import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.credential.service.CredentialService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

	private final AuthService authService;
	private final CredentialService credentialService;


	@Override
	@PostMapping
	public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest authRequest) {
		Auth auth = authService.signup(authRequest.toAuth());
		AuthResponse res = AuthResponse.from(auth);

		return ResponseEntity.ok(res);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest,
									  HttpServletResponse response) {
		authService.login(loginRequest, response);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		credentialService.removeCredential(response);
		return ResponseEntity.ok().build();
	}

}
