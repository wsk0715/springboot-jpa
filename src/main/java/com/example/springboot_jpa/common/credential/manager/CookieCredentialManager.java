package com.example.springboot_jpa.common.credential.manager;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * 쿠키를 사용해서 인증 정보를 관리하는 클래스
 */
@Component
public class CookieCredentialManager implements CredentialManager {

	@Value("${security.cookie.maxAge}")
	private int cookieMaxAge;

	@Value("${security.cookie.isSecure}")
	private boolean cookieIsSecure;

	@Value("${security.cookie.httpOnly}")
	private boolean cookieHttpOnly;

	@Value("${security.cookie.sameSite")
	private String cookieSameSite;

	private static final String COOKIE_NAME = "jwt";


	/**
	 * 쿠키에 담긴 JWT 토큰을 얻는다.
	 *
	 * @param request HttpServeletRequest 객체
	 * @return JWT 토큰 값
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
	 * JWT 토큰을 HTTP-Only 쿠키에 추가한다.
	 *
	 * @param response   HttpServletResponse 객체
	 * @param credential 인증 정보 DTO
	 */
	@Override
	public void setCredential(HttpServletResponse response, Credential credential) {
		// 1. 쿠키 생성
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, credential.jwt())
											  .path("/")                      // 전체 경로에서 사용 가능
											  .maxAge(cookieMaxAge)           // 쿠키 유효기간
											  .secure(cookieIsSecure)         // HTTPS를 사용할 경우에만 전송
											  .httpOnly(cookieHttpOnly)       // 자바스크립트에서 접근 불가 설정
											  .sameSite(cookieSameSite)       // CSRF 공격 방지를 위한 SameSite 설정
											  .build();

		// 2. 응답 헤더에 쿠키 설정
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}


	/**
	 * 쿠키에 담긴 JWT 토큰을 만료시킨다.
	 *
	 * @param response HttpServeletResponse 객체
	 */
	@Override
	public void expireCredential(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
											  .path("/")
											  .maxAge(0)  // 1. 만료시간을 0으로 설정 = 쿠키 만료
											  .secure(cookieIsSecure)
											  .httpOnly(cookieHttpOnly)
											  .sameSite(cookieSameSite)
											  .build();

		// 2. 응답 헤더에 만료된 쿠키 설정
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

}
