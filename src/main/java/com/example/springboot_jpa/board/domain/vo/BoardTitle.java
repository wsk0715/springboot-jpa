package com.example.springboot_jpa.board.domain.vo;

import com.example.springboot_jpa.exception.type.domain.ArgumentLengthException;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 게시글의 제목에 관한 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class BoardTitle {

	public static final int MAX_LENGTH = 255;
	public static final String BOARD_TITLE_BLANK_MESSAGE = "게시글 제목을 입력해주세요.";
	public static final String BOARD_TITLE_LENGTH_MESSAGE = "게시글 제목은 " + MAX_LENGTH + "자를 초과할 수 없습니다.";


	@Column(name = "title", nullable = false)
	private String value;

	protected BoardTitle() {
	}

	private BoardTitle(String title) {
		validate(title);
		this.value = title;
	}

	public static BoardTitle of(String title) {
		return new BoardTitle(title);
	}


	private void validate(String title) {
		if (title == null || title.isEmpty()) {
			throw new ArgumentNullException(BOARD_TITLE_BLANK_MESSAGE);
		}
		if (title.length() > MAX_LENGTH) {
			throw new ArgumentLengthException(BOARD_TITLE_LENGTH_MESSAGE);
		}
	}

}
