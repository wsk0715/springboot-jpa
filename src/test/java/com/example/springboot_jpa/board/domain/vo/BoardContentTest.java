package com.example.springboot_jpa.board.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardContentTest {

	@Test
	@DisplayName("유효한 내용으로 BoardContent가 생성된다.")
	void of() {
		// given: 유효한 게시글 내용 준비
		String expectedContent = "테스트 게시글 내용입니다.";

		// when: BoardContent 객체 생성
		BoardContent boardContent = BoardContent.of(expectedContent);

		// then: 생성된 객체의 값이 기대값과 일치하는지 검증
		assertEquals(expectedContent, boardContent.getValue());
	}

	@Test
	@DisplayName("내용이 null이면 예외가 발생한다.")
	void of_contentIsNull() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			BoardContent.of(null);
		});
	}

	@Test
	@DisplayName("내용이 비어있으면 예외가 발생한다.")
	void of_contentIsEmpty() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			BoardContent.of("");
		});
	}

}
