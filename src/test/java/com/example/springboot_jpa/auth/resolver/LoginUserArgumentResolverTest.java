package com.example.springboot_jpa.auth.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@ExtendWith(MockitoExtension.class)
class LoginUserArgumentResolverTest {

	@Mock
	private AuthService authService;

	@Mock
	private MethodParameter methodParameter;

	@Mock
	private ModelAndViewContainer modelAndViewContainer;

	@Mock
	private NativeWebRequest webRequest;

	@Mock
	private WebDataBinderFactory binderFactory;

	@Mock
	private HttpServletRequest httpServletRequest;

	@InjectMocks
	private LoginUserArgumentResolver loginUserArgumentResolver;


	@Test
	@DisplayName("유효한 토큰으로부터 User 객체를 정상적으로 반환한다.")
	void resolveArgument() {
		// given: 테스트에 필요한 데이터 설정
		String expectedToken = "valid-jwt-token";
		Nickname expectedNickname = Nickname.of("testUser");
		User expectedUser = User.create(expectedNickname);

		// authService 모킹
		when(webRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(httpServletRequest);
		when(authService.getCredential(httpServletRequest)).thenReturn(expectedToken);
		when(authService.extractFromToken(expectedToken)).thenReturn(expectedUser);

		// when: resolveArgument 메소드 호출
		User resultUser = loginUserArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, binderFactory);

		// then: 반환된 User 객체가 예상한 값과 일치하는지 검증
		assert resultUser != null;
		assertEquals(expectedUser, resultUser);
		assertEquals(expectedNickname, resultUser.getNickname());
	}

	@Test
	@DisplayName("토큰이 존재하지 않는 경우 null을 반환한다.")
	void resolveArgument_tokenIsNull() {
		// given: 토큰이 null을 반환하도록 모킹
		when(webRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(httpServletRequest);
		when(authService.getCredential(httpServletRequest)).thenReturn(null);

		// when: resolveArgument 메소드 호출
		User resultUser = loginUserArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, binderFactory);

		// then: null이 반환되는지 검증
		assertNull(resultUser);
	}

	@Test
	@DisplayName("토큰이 빈 문자열인 경우 null을 반환한다.")
	void resolveArgument_tokenIsEmpty() {
		// given: 토큰이 빈 문자열을 반환하도록 모킹
		when(webRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(httpServletRequest);
		when(authService.getCredential(httpServletRequest)).thenReturn("");

		// when: resolveArgument 메소드 호출
		User resultUser = loginUserArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, binderFactory);

		// then: null이 반환되는지 검증
		assertNull(resultUser);
	}

}
