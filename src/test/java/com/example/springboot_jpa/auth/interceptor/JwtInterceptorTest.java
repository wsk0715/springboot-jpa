package com.example.springboot_jpa.auth.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.springboot_jpa.BaseSpringBootTest;
import com.example.springboot_jpa.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class JwtInterceptorTest extends BaseSpringBootTest {

	@MockBean
	private AuthService authService;

	@Autowired
	private JwtInterceptor jwtInterceptor;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private String dummyToken = "dummyToken";

	@BeforeEach
	void setUp() {
		// given
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}

	@Test
	@DisplayName("토큰이 유효하면 preHandle이 true를 반환한다.")
	void preHandle() {
		// 요청에서 임의 토큰값 추출하도록 모킹
		when(authService.extractJwtFromRequest(request)).thenReturn(dummyToken);

		// validateToken()이 true를 반환하도록 모킹
		when(authService.validateToken(dummyToken)).thenReturn(true);

		// when: preHandle 호출
		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		// then: 결과가 true이며, 상태 코드가 401이 아닌지 검증
		assertTrue(result);
		assertThat(response.getStatus()).isNotEqualTo(HttpStatus.UNAUTHORIZED.value());
	}


	@Test
	@DisplayName("토큰이 null인 경우 false를 반환하고, 401 상태 코드를 설정한다.")
	void preHandle_tokenIsNull() {
		// 추출된 토큰이 null 값을 갖도록 모킹
		when(authService.extractJwtFromRequest(request)).thenReturn(null);

		// when: preHandle 호출
		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		// then: 결과가 false이며, response의 상태 코드가 401(UNAUTHORIZED)로 설정되었는지 검증
		assertFalse(result);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	@DisplayName("토큰이 빈 문자열인 경우 false를 반환하고, 401 상태 코드를 설정한다.")
	void preHandle_tokenIsEmpty() {
		// 추출된 토큰이 빈 문자열을 갖도록 모킹
		when(authService.extractJwtFromRequest(request)).thenReturn("");

		// when: preHandle 호출
		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		// then: 결과가 false이며, 상태 코드가 401로 설정되었는지 검증
		assertFalse(result);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	@DisplayName("토큰이 유효하지 않으면 false를 반환하고, 401 상태 코드를 설정한다.")
	void preHandle_invalidToken() {
		// 요청에서 임의 토큰값 추출하도록 모킹
		when(authService.extractJwtFromRequest(request)).thenReturn(dummyToken);

		// 유효하지 않은 토큰으로 가정하여 validateToken()이 false를 반환하도록 모킹
		when(authService.validateToken(dummyToken)).thenReturn(false);

		// when: preHandle 호출
		boolean result = jwtInterceptor.preHandle(request, response, new Object());

		// then: 결과가 false이며, 상태 코드가 401로 설정되었는지 검증
		assertFalse(result);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

}
