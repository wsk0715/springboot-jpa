package com.example.springboot_jpa.auth.controller;

import com.example.springboot_jpa.auth.controller.request.AuthRequest;
import com.example.springboot_jpa.auth.controller.request.LoginRequest;
import com.example.springboot_jpa.auth.service.AuthService;
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


	@Override
	@PostMapping
	public ResponseEntity<Void> signup(@RequestBody AuthRequest authRequest) {
		authService.signup(authRequest.toAuth());
		return ResponseEntity.ok().build();
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
		authService.removeCredential(response);
		return ResponseEntity.ok().build();
	}

}
