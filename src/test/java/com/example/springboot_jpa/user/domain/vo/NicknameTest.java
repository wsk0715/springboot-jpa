package com.example.springboot_jpa.user.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NicknameTest {

	@Test
	@DisplayName("유효한 닉네임으로 Nickname 객체가 생성된다.")
	void of() {
		// given: 유효한 닉네임 준비
		String expectedNickname = "테스트닉네임123";

		// when: Nickname 객체 생성
		Nickname nickname = Nickname.of(expectedNickname);

		// then: 생성된 객체의 값이 기대값과 일치하는지 검증
		assertEquals(expectedNickname, nickname.getValue());
	}

	@Test
	@DisplayName("닉네임 앞뒤 공백이 제거된 Nickname 객체가 생성된다.")
	void of_trimNickname() {
		// given: 앞뒤 공백이 있는 닉네임 준비
		String nicknameWithSpaces = " 테스트닉네임 ";
		String expectedNickname = "테스트닉네임";

		// when: Nickname 객체 생성
		Nickname nickname = Nickname.of(nicknameWithSpaces);

		// then: 생성된 객체의 값이 공백이 제거된 기대값과 일치하는지 검증
		assertEquals(expectedNickname, nickname.getValue());
	}

	@Test
	@DisplayName("닉네임이 null이면 예외가 발생한다.")
	void of_nicknameIsNull() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			Nickname.of(null);
		});
	}

	@Test
	@DisplayName("닉네임이 비어있으면 예외가 발생한다.")
	void of_nicknameIsEmpty() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			Nickname.of("");
		});
	}

	@Test
	@DisplayName("닉네임이 최대 길이를 초과하면 예외가 발생한다.")
	void of_nicknameExceedsMaxLength() {
		// given: 최대 길이를 초과하는 닉네임 준비
		String tooLongNickname = "a".repeat(Nickname.NICKNAME_MAX_LENGTH + 1);

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			Nickname.of(tooLongNickname);
		});
	}

	@Test
	@DisplayName("닉네임이 형식에 맞지 않으면 예외가 발생한다.")
	void of_nicknameInvalidFormat() {
		// given: 형식에 맞지 않는 닉네임 준비 (특수문자 허용x)
		String invalidNickname = "테스트닉네임!@#";

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			Nickname.of(invalidNickname);
		});
	}

}
