package com.example.springboot_jpa.common.credential.manager;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * 쿠키를 사용해서 인증 정보를 관리하는 클래스
 */
@Component
@Primary
public class CookieCredentialManager implements CredentialManager {

	@Value("${security.cookie.maxAge}")
	private int maxAge;

	@Value("${security.cookie.isSecure}")
	private boolean isSecure;

	@Value("${security.cookie.httpOnly}")
	private boolean httpOnly;

	@Value("${security.cookie.sameSite}")
	private String sameSite;

	private static final String COOKIE_NAME = "jwt";


	/**
	 * 인증 정보를 쿠키에 추가한다.
	 */
	@Override
	public void setCredential(HttpServletResponse response, Credential credential) {
		ResponseCookie cookie = createCookie(maxAge, credential.jwt());
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	private ResponseCookie createCookie(int age, String jwt) {
		return ResponseCookie.from(COOKIE_NAME, jwt)
							 .path("/")           // 전체 경로에서 사용 가능
							 .maxAge(age)         // 쿠키 유효기간
							 .secure(isSecure)    // HTTPS를 사용할 경우에만 전송
							 .httpOnly(httpOnly)  // 자바스크립트에서 접근 가능여부 설정
							 .sameSite(sameSite)  // CSRF 공격 방지를 위한 SameSite 설정
							 .build();
	}

	/**
	 * 쿠키에 담긴 인증 정보를 얻는다.
	 */
	@Override
	public String getCredential(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			throw new SpringbootJpaException("쿠키가 존재하지 않습니다.");
		}

		for (Cookie cookie : request.getCookies()) {
			if (COOKIE_NAME.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		throw new SpringbootJpaException("쿠키에 인증 정보가 존재하지 않습니다.");
	}

	/**
	 * 쿠키에 담긴 인증 정보를 제거한다.
	 */
	@Override
	public void removeCredential(HttpServletResponse response) {
		ResponseCookie cookie = createCookie(0, "");  // 만료시간을 0으로 설정 - 쿠키 만료

		// 2. 응답 헤더에 만료된 쿠키 설정
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

}
