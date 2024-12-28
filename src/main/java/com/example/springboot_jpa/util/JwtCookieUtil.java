package com.example.springboot_jpa.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieUtil {

	@Value("${security.cookie.httpOnly}")
	private boolean COOKIE_HTTP_ONLY;

	@Value("${security.cookie.isSecure}")
	private boolean COOKIE_IS_SECURE;

	@Value("${security.cookie.maxAge}")
	private int COOKIE_MAX_AGE;


	/**
	 * JWT 토큰을 HTTP-Only 쿠키에 추가한다.
	 *
	 * @param response HttpServletResponse 객체
	 * @param token    발급받은 JWT 토큰
	 */
	public void addJwtToCookie(HttpServletResponse response, String token) {
		// 1. 쿠키 생성
		ResponseCookie cookie = ResponseCookie.from("jwt", token)
											  .httpOnly(COOKIE_HTTP_ONLY)       // 자바스크립트에서 접근 불가 설정
											  .secure(COOKIE_IS_SECURE)         // HTTPS를 사용할 경우에만 전송
											  .path("/")                        // 전체 경로에서 사용 가능
											  .maxAge(COOKIE_MAX_AGE)           // 쿠키 유효기간
											  .sameSite("Strict")               // CSRF 공격 방지를 위한 SameSite 설정 [Lax | Strict]
											  .build();

		// 2. 응답 헤더에 쿠키 추가
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	/**
	 * 쿠키에 담긴 JWT 토큰을 만료시킨다.
	 *
	 * @param response HttpServeletResponse 객체
	 */
	public void expireJwtFromToken(HttpServletResponse response) {
		String EMPTY_VALUE = "";
		ResponseCookie cookie = ResponseCookie.from("jwt", EMPTY_VALUE)
											  .httpOnly(COOKIE_HTTP_ONLY)
											  .secure(COOKIE_IS_SECURE)
											  .path("/")
											  .maxAge(0)  // 만료시간을 0으로 설정 = 쿠키 만료
											  .sameSite("Strict")
											  .build();

		// 2. 응답 헤더에 쿠키 삭제 명령 추가
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	/**
	 * 쿠키에 담긴 JWT 토큰을 얻는다.
	 *
	 * @param request HttpServeletRequest 객체
	 * @return JWT 토큰 값
	 */
	public String getJwtFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if ("jwt".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
