package com.example.springboot_jpa.board.domain.vo;

import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 게시글의 내용에 관한 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class BoardContent {

	public static final String BOARD_CONTENT_BLANK_MESSAGE = "게시글 본문을 입력해주세요.";


	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String value;

	protected BoardContent() {
	}

	private BoardContent(String content) {
		validate(content);
		this.value = content;
	}

	public static BoardContent of(String content) {
		return new BoardContent(content);
	}


	private void validate(String content) {
		if (content == null || content.isEmpty()) {
			throw new ArgumentNullException(BOARD_CONTENT_BLANK_MESSAGE);
		}
	}

}
