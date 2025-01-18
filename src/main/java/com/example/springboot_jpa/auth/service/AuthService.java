package com.example.springboot_jpa.auth.service;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.credential.manager.CredentialManager;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.common.util.JwtTokenUtil;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	private final List<CredentialManager> credentialManager;

	private final JwtTokenUtil jwtTokenUtil;


	public Credential setCredential(HttpServletResponse response, String token) {
		Credential credential = new Credential(token);

		credentialManager.forEach(cm -> cm.setCredential(response, credential));
		return credential;
	}

	public void removeCredential(HttpServletResponse response) {
		credentialManager.forEach(cm -> cm.removeCredential(response));
	}

	public String getCredential(HttpServletRequest request) {
		for (CredentialManager cm : credentialManager) {
			if (cm.hasCredential(request)) {
				return cm.getCredential(request);
			}
		}
		throw new SpringbootJpaException("인증 정보를 찾을 수 없습니다.");
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
