package com.example.springboot_jpa.credential.manager.header;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import com.example.springboot_jpa.credential.dto.Credential;
import com.example.springboot_jpa.credential.manager.CredentialManager;
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
		if (!hasCredential(request)) {
			throw new SpringbootJpaException("헤더에 인증 정보가 존재하지 않습니다.");
		}

		String[] authorization = request.getHeader("Authorization").split(" ");
		if (authorization.length < 2 || !"Bearer".equals(authorization[0])) {
			throw new SpringbootJpaException("헤더의 인증 정보가 잘못되었습니다.");
		}

		return authorization[1];
	}

	/**
	 * 헤더에 인증 정보가 존재하는지 확인한다.
	 */
	@Override
	public boolean hasCredential(HttpServletRequest httpServletRequest) {
		String header = httpServletRequest.getHeader("Authorization");
		return header != null && !header.isEmpty();
	}

	/**
	 * 헤더의 인증 정보를 제거한다.
	 */
	@Override
	public void removeCredential(HttpServletResponse response) {
		response.setHeader("Authorization", "");
	}

}
