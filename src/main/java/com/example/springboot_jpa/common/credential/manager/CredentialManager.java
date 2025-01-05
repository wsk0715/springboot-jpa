package com.example.springboot_jpa.common.credential.manager;

import com.example.springboot_jpa.common.credential.dto.Credential.Credential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 인증 정보를 관리하는 클래스를 정의하는 인터페이스
 */
public interface CredentialManager {

	void setCredential(HttpServletResponse httpServletResponse, Credential credential);

	String getCredential(HttpServletRequest httpServletRequest);

	void expireCredential(HttpServletResponse httpServletResponse);

}
