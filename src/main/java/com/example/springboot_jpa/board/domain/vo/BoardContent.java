package com.example.springboot_jpa.board.domain.vo;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 게시글의 내용에 관한 도메인 로직을 담은 VO
 */
@Getter
@Embeddable
public class BoardContent {

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String value;

	protected BoardContent() {
	}

	public BoardContent(String content) {
		validate(content);
		this.value = content;
	}

	public static BoardContent of(String content) {
		return new BoardContent(content);
	}


	private void validate(String content) {
		if (content == null || content.isEmpty()) {
			throw new SpringbootJpaException("게시글 본문을 입력해주세요.");
		}
	}

}
