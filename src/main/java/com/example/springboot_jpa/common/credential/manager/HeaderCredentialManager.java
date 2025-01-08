package com.example.springboot_jpa.common.credential.manager;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderCredentialManager implements CredentialManager {
	
	/**
	 * 헤더에 인증 정보를 설정한다.
	 */
	@Override
	public void setCredential(HttpServletResponse response, Credential credential) {
		response.setHeader("Authorization", "Bearer " + credential.jwt());
	}

	/**
	 * 헤더에서 인증 정보를 불러온다.
	 */
	@Override
	public String getCredential(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || header.isEmpty()) {
			throw new SpringbootJpaException("헤더에 인증 정보가 존재하지 않습니다.");
		}

		String[] authorization = header.split(" ");
		if (authorization.length < 2 || !"Bearer".equals(authorization[0])) {
			throw new SpringbootJpaException("잘못된 인증 정보입니다.");
		}

		return authorization[1];
	}

	/**
	 * 헤더의 인증 정보를 제거한다.
	 */
	@Override
	public void removeCredential(HttpServletResponse response) {
		response.setHeader("Authorization", "");
	}

}
