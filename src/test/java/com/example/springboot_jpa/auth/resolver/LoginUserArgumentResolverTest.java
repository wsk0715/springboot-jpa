package com.example.springboot_jpa.auth.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginUserArgumentResolverTest {

	@Test
	@DisplayName("유효한 토큰으로부터 User 객체를 정상적으로 반환한다.")
	void resolveArgument() {
	}

	@Test
	@DisplayName("토큰이 존재하지 않는 경우 예외를 던진다.")
	void resolveArgument_tokenIsNull() {
	}

	@Test
	@DisplayName("토큰이 빈 문자열인 경우 null을 반환한다.")
	void resolveArgument_tokenIsEmpty() {
	}

}
