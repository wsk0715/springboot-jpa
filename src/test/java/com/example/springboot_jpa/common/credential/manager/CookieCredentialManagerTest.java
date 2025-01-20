package com.example.springboot_jpa.common.credential.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springboot_jpa.BaseSpringBootTest;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.credential.dto.Credential;
import com.example.springboot_jpa.credential.manager.cookie.CookieCredentialManager;
import com.example.springboot_jpa.credential.manager.cookie.properties.CookieCredentialProperties;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
@EnableConfigurationProperties(CookieCredentialProperties.class)
class CookieCredentialManagerTest extends BaseSpringBootTest {

	@Autowired
	private CookieCredentialProperties cookieCredentialProperties;
	private CookieCredentialManager credentialManager;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeEach
	void setUp() {
		credentialManager = new CookieCredentialManager(cookieCredentialProperties);
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}


	@Test
	@DisplayName("쿠키에 인증 정보가 정상적으로 저장된다.")
	void setCredential() {
		// given: 인증 정보 생성
		String expectedJwt = "test-jwt-token";
		Credential credential = new Credential(expectedJwt);

		// when: 인증 정보 설정 메소드 호출
		credentialManager.setCredential(response, credential);

		// then: 응답에 인증 정보가 포함되었는지 검증
		assertThat(response.getHeader(HttpHeaders.SET_COOKIE)).contains(expectedJwt);

	}

	@Test
	@DisplayName("쿠키에서 인증 정보를 성공적으로 불러온다.")
	void getCredential() {
		// given: 요청에 인증 정보를 담은 쿠키 추가
		String expectedJwt = "valid-jwt-token";
		request.setCookies(new Cookie("jwt", expectedJwt));

		// when: getCredential 메소드 호출
		String jwt = credentialManager.getCredential(request);

		// then: 메소드가 반환한 값과 기대값이 일치하는지 검증
		assertEquals(expectedJwt, jwt);
	}

	@Test
	@DisplayName("쿠키가 없으면 예외가 발생한다.")
	void getCredential_invalidHeader() {
		// given

		// when & then: 쿠키가 빈 요청에 대해 예외를 던지는지 확인
		assertThrows(SpringbootJpaException.class, () -> {
			credentialManager.getCredential(request);
		});
	}

	@Test
	@DisplayName("잘못된 인증 정보에 대해 예외를 던진다.")
	void getCredential_invalidToken() {
		// given
		request.setCookies(new Cookie("jwt", ""));

		// when & then: 잘못된 인증 정보에 대해 예외를 던지는지 확인
		assertThrows(SpringbootJpaException.class, () -> {
			credentialManager.getCredential(request);
		});
	}

	@Test
	@DisplayName("쿠키에 인증 정보가 존재하는지 확인한다.")
	void hasCredential() {
		// given: 쿠키에 인증 정보 존재
		String expectedJwt = "valid-jwt-token";
		request.setCookies(new Cookie("jwt", expectedJwt));

		// when & then: 쿠키에 인증 정보가 존재하는지 확인
		assertTrue(credentialManager.hasCredential(request));
	}

	@Test
	@DisplayName("쿠키에 인증 정보가 존재하는지 확인한다.")
	void hasCredential_noCredential() {
		// given: 쿠키에 인증 정보 존재 x

		// when & then: 쿠키에 인증 정보가 존재하는지 확인
		assertFalse(credentialManager.hasCredential(request));
	}

	@Test
	@DisplayName("쿠키의 인증 정보가 정상적으로 제거된다.")
	void removeCredential() {
		// given: 쿠키의 인증 정보 설정
		String expectedJwt = "valid-jwt-token";
		response.addCookie(new Cookie("jwt", expectedJwt));

		// when: 인증 만료 메소드 호출
		credentialManager.removeCredential(response);

		// then: 쿠키의 만료 시간이 1보다 작은지 확인
		assertThat(response.getCookie("jwt").getMaxAge() < 1);
	}

}
