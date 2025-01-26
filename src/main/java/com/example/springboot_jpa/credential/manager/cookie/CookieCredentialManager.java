package com.example.springboot_jpa.credential.manager.cookie;

import com.example.springboot_jpa.credential.dto.Credential;
import com.example.springboot_jpa.credential.manager.CredentialManager;
import com.example.springboot_jpa.credential.manager.cookie.properties.CookieCredentialProperties;
import com.example.springboot_jpa.exception.type.status4xx.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * 쿠키를 사용해서 인증 정보를 관리하는 클래스
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(CookieCredentialProperties.class)
public class CookieCredentialManager implements CredentialManager {

	private final CookieCredentialProperties cookieCredentialProperties;


	/**
	 * 인증 정보를 쿠키에 추가한다.
	 */
	@Override
	public void setCredential(HttpServletResponse response, Credential credential) {
		ResponseCookie cookie = createCookie(cookieCredentialProperties.maxAge(), credential.jwt());
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	private ResponseCookie createCookie(int age, String jwt) {
		return ResponseCookie.from(cookieCredentialProperties.cookieName(), jwt)
							 .path("/")                                        // 전체 경로에서 사용 가능
							 .maxAge(age)                                      // 쿠키 유효기간
							 .secure(cookieCredentialProperties.isSecure())    // HTTPS를 사용할 경우에만 전송
							 .httpOnly(cookieCredentialProperties.httpOnly())  // 자바스크립트에서 접근 가능여부 설정
							 .sameSite(cookieCredentialProperties.sameSite())  // CSRF 공격 방지를 위한 SameSite 설정
							 .build();
	}

	/**
	 * 쿠키에 담긴 인증 정보를 얻는다.
	 */
	@Override
	public String getCredential(HttpServletRequest request) {
		Cookie cookie = findCredentialCookie(request);
		if (cookie == null) {
			throw new UnauthorizedException("쿠키에 인증 정보가 존재하지 않습니다.");
		}

		String credential = cookie.getValue();
		if (credential == null || credential.isEmpty()) {
			throw new UnauthorizedException("쿠키의 인증 정보가 유효하지 않습니다.");
		}

		return credential;
	}

	private Cookie findCredentialCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookieCredentialProperties.cookieName().equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 쿠키에 인증 정보가 존재하는지 확인한다.
	 */
	@Override
	public boolean hasCredential(HttpServletRequest request) {
		return findCredentialCookie(request) != null;
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
