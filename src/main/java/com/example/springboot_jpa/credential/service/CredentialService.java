package com.example.springboot_jpa.credential.service;

import com.example.springboot_jpa.credential.JwtTokenUtil;
import com.example.springboot_jpa.credential.dto.Credential;
import com.example.springboot_jpa.credential.manager.CredentialManager;
import com.example.springboot_jpa.exception.type.status4xx.UnauthorizedException;
import com.example.springboot_jpa.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialService {

	private final List<CredentialManager> credentialManager;
	private final JwtTokenUtil jwtTokenUtil;


	public Credential setCredential(HttpServletResponse response, User user) {
		String token = jwtTokenUtil.createToken(user);
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
				String token = cm.getCredential(request);
				validateToken(token);
				return token;
			}
		}
		throw new UnauthorizedException("인증 정보를 찾을 수 없습니다.");
	}

	private void validateToken(String token) {
		if (!jwtTokenUtil.validateToken(token)) {
			throw new UnauthorizedException("유효하지 않은 인증 정보입니다.");
		}
	}

	public Long getUserId(String token) {
		return jwtTokenUtil.getUserId(token);
	}

}
