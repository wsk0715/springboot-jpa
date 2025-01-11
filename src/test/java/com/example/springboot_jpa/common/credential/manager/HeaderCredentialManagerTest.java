package com.example.springboot_jpa.common.credential.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class HeaderCredentialManagerTest {

	private HeaderCredentialManager credentialManager;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeEach
	void setUp() {
		credentialManager = new HeaderCredentialManager();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}


	@Test
	@DisplayName("헤더에 인증 정보가 정상적으로 저장된다.")
	void setCredential() {
		// given: 인증 정보 생성
		String expectedJwt = "test-jwt-token";
		Credential credential = new Credential(expectedJwt);

		// when: 인증 정보 설정 메소드 호출
		credentialManager.setCredential(response, credential);

		// then: 요청에서 추출한 헤더와 기대값이 일치하는지 검증
		assertEquals("Bearer " + expectedJwt, response.getHeader("Authorization"));
	}

	@Test
	@DisplayName("헤더에서 인증 정보를 성공적으로 불러온다.")
	void getCredential() {
		// given: 요청에 인증 정보를 담은 헤더 추가
		String expectedJwt = "valid-jwt-token";
		request.addHeader("Authorization", "Bearer " + expectedJwt);

		// when: getCredential 메소드 호출
		String jwt = credentialManager.getCredential(request);

		// then: 메소드가 반환한 값과 기대값이 일치하는지 검증
		assertEquals(expectedJwt, jwt);
	}

	@Test
	@DisplayName("헤더가 없으면 예외가 발생한다.")
	void getCredential_invalidHeader() {
		// given

		// when & then: 헤더가 빈 요청에 대해 예외를 던지는지 확인
		assertThrows(SpringbootJpaException.class, () -> {
			credentialManager.getCredential(request);
		});
	}

	@Test
	@DisplayName("잘못된 인증 정보에 대해 예외를 던진다.")
	void getCredential_invalidToken() {
		// given
		request.addHeader("Authorization", "InvalidFormatToken");

		// when & then: 잘못된 인증 정보에 대해 예외를 던지는지 확인
		assertThrows(SpringbootJpaException.class, () -> {
			credentialManager.getCredential(request);
		});
	}

	@Test
	@DisplayName("헤더의 인증 정보가 정상적으로 제거된다.")
	void expireCredential() {
		// given: 헤더의 인증 정보 설정
		response.setHeader("Authorization", "Bearer some-token");

		// when: 인증 만료 메소드 호출
		credentialManager.removeCredential(response);

		// then: 헤더에 인증 정보가 존재하지 않는지 확인
		String expectedValue = "";
		assertEquals(expectedValue, response.getHeader("Authorization"));
	}

}
