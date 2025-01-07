package com.example.springboot_jpa.auth.service;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.credential.manager.CredentialManager;
import com.example.springboot_jpa.common.util.JwtTokenUtil;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	private final CredentialManager credentialManager;

	private final JwtTokenUtil jwtTokenUtil;


	public Credential setCredential(HttpServletResponse response, String token) {
		Credential credential = new Credential(token);
		credentialManager.setCredential(response, credential);
		return credential;
	}

	public String extractJwtFromRequest(HttpServletRequest request) {
		return credentialManager.getCredential(request);
	}

	public boolean validateToken(String token) {
		return jwtTokenUtil.validateToken(token);
	}

	public Long getUserId(String token) {
		return jwtTokenUtil.getUserId(token);
	}

	public User extractFromToken(String token) {
		return userService.findById(getUserId(token));
	}

}
