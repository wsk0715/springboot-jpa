package com.example.springboot_jpa.board.domain.vo;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 게시글의 제목에 관한 도메인 로직을 담은 VO
 */
@Getter
@Embeddable
public class Title {

	public static final int MAX_LENGTH = 255;

	@Column(name = "title", nullable = false)
	private String value;

	protected Title() {
	}

	public Title(String title) {
		validate(title);
		this.value = title;
	}

	public static Title of(String title) {
		return new Title(title);
	}


	public void update(String title) {
		validate(title);
		this.value = title;
	}

	private void validate(String title) {
		if (title == null || title.isEmpty()) {
			throw new SpringbootJpaException("게시글 제목을 입력해주세요.");
		}
		if (title.length() > MAX_LENGTH) {
			throw new SpringbootJpaException("게시글 제목은 " + MAX_LENGTH + "자를 초과할 수 없습니다.");
		}
	}

}
