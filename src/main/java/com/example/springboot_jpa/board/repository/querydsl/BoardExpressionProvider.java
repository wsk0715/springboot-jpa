package com.example.springboot_jpa.board.repository.querydsl;

import static com.example.springboot_jpa.board.domain.QBoard.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class BoardExpressionProvider {

	public BooleanExpression userIdEq(Long userId) {
		return userId != null ? board.user.id.eq(userId) : null;
	}

	public BooleanExpression userNicknameEq(String nickname) {
		return nickname != null ? board.user.nickname.value.eq(nickname) : null;
	}

	public BooleanExpression titleOrContentContains(String titleOrContent, String title, String content) {
		if (titleOrContent != null) {
			return board.title.value.contains(titleOrContent)
									.or(board.content.value.contains(titleOrContent));
		} else {
			return titleContains(title).and(contentContains(content));
		}
	}

	public BooleanExpression titleContains(String title) {
		return title != null ? board.title.value.contains(title) : null;
	}

	public BooleanExpression contentContains(String content) {
		return content != null ? board.content.value.contains(content) : null;
	}

	public BooleanExpression createdAtGoe(LocalDateTime dateTimeFrom) {
		return dateTimeFrom != null ? board.createdAt.goe(dateTimeFrom) : null;
	}

	public BooleanExpression createdAtLoe(LocalDateTime dateTimeTo) {
		return dateTimeTo != null ? board.createdAt.loe(dateTimeTo) : null;
	}

}
