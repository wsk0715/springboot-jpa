package com.example.springboot_jpa.comment.domain.vo;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 댓글의 내용에 관한 도메인 로직을 담은 VO
 */
@Getter
@Embeddable
public class CommentContent {

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String value;

	protected CommentContent() {
	}

	private CommentContent(String content) {
		validate(content);
		this.value = content;
	}

	public static CommentContent of(String content) {
		return new CommentContent(content);
	}

	private void validate(String content) {
		if (content == null || content.isEmpty()) {
			throw new SpringbootJpaException("본문을 작성해주세요.");
		}
	}

}

