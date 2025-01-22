package com.example.springboot_jpa.board.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTitleTest {

	@Test
	@DisplayName("유효한 제목으로 BoardTitle이 생성된다.")
	void of() {
		// given: 유효한 게시글 제목 준비
		String expectedTitle = "테스트 게시글 제목";

		// when: BoardTitle 객체 생성
		BoardTitle boardTitle = BoardTitle.of(expectedTitle);

		// then: 생성된 객체의 값이 기대값과 일치하는지 검증
		assertEquals(expectedTitle, boardTitle.getValue());
	}

	@Test
	@DisplayName("제목이 null이면 예외가 발생한다.")
	void of_titleIsNull() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			BoardTitle.of(null);
		});
	}

	@Test
	@DisplayName("제목이 비어있으면 예외가 발생한다.")
	void of_titleIsEmpty() {
		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			BoardTitle.of("");
		});
	}

	@Test
	@DisplayName("제목이 최대 길이를 초과하면 예외가 발생한다.")
	void of_titleExceedsMaxLength() {
		// given: 최대 길이를 초과하는 제목 준비
		String tooLongTitle = "a".repeat(BoardTitle.MAX_LENGTH + 1);

		// when & then: 예외 발생 확인
		assertThrows(SpringbootJpaException.class, () -> {
			BoardTitle.of(tooLongTitle);
		});
	}

}
