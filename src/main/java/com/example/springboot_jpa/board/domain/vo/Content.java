package com.example.springboot_jpa.board.domain.vo;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Content {

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String value;

	protected Content() {
	}

	public Content(String content) {
		validate(content);
		this.value = content;
	}

	public static Content of(String content) {
		return new Content(content);
	}

	
	public void update(String content) {
		validate(content);
		this.value = content;
	}

	private void validate(String content) {
		if (content == null || content.isEmpty()) {
			throw new SpringbootJpaException("게시글 본문을 입력해주세요.");
		}
	}

}
