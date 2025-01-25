package com.example.springboot_jpa.auth.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthLoginIdTest {

	@Test
	@DisplayName("로그인 아이디가 성공적으로 생성된다.")
	void of() {
		// given: 유효한 로그인 아이디 준비
		String validLoginId = "testuser123";

		// when: AuthLoginId 객체 생성
		AuthLoginId authLoginId = AuthLoginId.of(validLoginId);

		// then: 생성된 객체의 값 검증
		assertEquals(validLoginId, authLoginId.getValue());
	}

	@Test
	@DisplayName("로그인 아이디가 null일 경우 예외가 발생한다.")
	void of_nullLoginId() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthLoginId.of(null);
		});
	}

	@Test
	@DisplayName("로그인 아이디가 빈 문자열일 경우 예외가 발생한다.")
	void of_emptyLoginId() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthLoginId.of("");
		});
	}

	@Test
	@DisplayName("로그인 아이디가 최소 길이보다 짧을 경우 생성 시 예외가 발생한다.")
	void of_tooShortLoginId() {
		// given: 최소 길이보다 짧은 로그인 아이디
		String tooShortLoginId = "a".repeat(AuthLoginId.LOGIN_ID_MIN_LENGTH - 1);

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthLoginId.of(tooShortLoginId);
		});
	}

	@Test
	@DisplayName("로그인 아이디가 최대 길이보다 길 경우 생성 시 예외가 발생한다.")
	void of_tooLongLoginId() {
		// given: 최대 길이보다 긴 로그인 아이디
		String tooLongLoginId = "a".repeat(AuthLoginId.LOGIN_ID_MAX_LENGTH + 1);

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthLoginId.of(tooLongLoginId);
		});
	}

	@Test
	@DisplayName("toString 메서드가 value 값을 반환한다.")
	void testToString() {
		// given: 유효한 로그인 아이디로 객체 생성
		String expectedValue = "testuser123";
		AuthLoginId authLoginId = AuthLoginId.of(expectedValue);

		// when: toString 메서드 호출
		String result = authLoginId.toString();

		// then: 결과 검증
		assertEquals(expectedValue, result);
	}

}
