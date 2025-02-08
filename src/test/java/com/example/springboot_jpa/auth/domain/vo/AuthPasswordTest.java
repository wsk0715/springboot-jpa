package com.example.springboot_jpa.auth.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthPasswordTest {

	@Test
	@DisplayName("비밀번호가 성공적으로 생성된다.")
	void of() {
		// given: 유효한 비밀번호 준비
		String validPassword = "validPassword123*";

		// when: AuthPassword 객체 생성
		AuthPassword authPassword = AuthPassword.of(validPassword);

		// then: 생성된 객체가 null이 아닌지 확인
		assertNotNull(authPassword);
		assertNotNull(authPassword.getValue());
	}

	@Test
	@DisplayName("비밀번호가 null일 경우 생성 시 예외가 발생한다.")
	void of_nullPassword() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthPassword.of(null);
		});
	}

	@Test
	@DisplayName("비밀번호가 빈 문자열일 경우 생성 시 예외가 발생한다.")
	void of_emptyPassword() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthPassword.of("");
		});
	}

	@Test
	@DisplayName("비밀번호가 최소 길이보다 짧은 경우 생성 시 예외가 발생한다.")
	void of_tooShortPassword() {
		// given: 최소 길이보다 짧은 비밀번호
		String tooShortPassword = "S1*";

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			AuthPassword.of(tooShortPassword);
		});
	}

	@Test
	@DisplayName("비밀번호 일치 여부를 올바르게 검증한다.")
	void isEquals() {
		// given: 비밀번호와 AuthPassword 객체 생성
		String originalPassword = "validPassword123*";
		AuthPassword authPassword = AuthPassword.of(originalPassword);

		// when & then: 동일한 비밀번호로 검증 시 true 반환
		assertTrue(authPassword.isMatches(originalPassword));

		// when & then: 다른 비밀번호로 검증 시 false 반환
		assertFalse(authPassword.isMatches("differentPassword123"));
	}

	@Test
	@DisplayName("toString 메서드가 value 값을 반환한다.")
	void testToString() {
		// given: 유효한 비밀번호로 객체 생성
		String originalPassword = "validPassword123*";
		AuthPassword authPassword = AuthPassword.of(originalPassword);

		// when: toString 메서드 호출
		String result = authPassword.toString();

		// then: 결과 검증 (암호화된 값이므로 원본과 다름)
		assertNotNull(result);
		assertNotEquals(originalPassword, result);
		assertEquals(authPassword.getValue(), result);
	}

}
