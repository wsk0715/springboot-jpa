package com.example.springboot_jpa.board.repository.querydsl;

import static com.example.springboot_jpa.board.domain.QBoard.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BoardExpressionProvider {

	public BooleanExpression userIdEq(Long userId) {
		return Optional.ofNullable(userId)
					   .map(board.user.id::eq)
					   .orElse(null);
	}

	public BooleanExpression userNicknameEq(String nickname) {
		return Optional.ofNullable(nickname)
					   .map(board.user.nickname.value::eq)
					   .orElse(null);
	}

	public BooleanExpression titleOrContentContains(String titleOrContent, String title, String content) {
		return Optional.ofNullable(titleOrContent)
					   .map(value -> board.title.value.contains(value)
													  .or(board.content.value.contains(value)))
					   .orElseGet(() -> titleContains(title).and(contentContains(content)));
	}

	public BooleanExpression titleContains(String title) {
		return Optional.ofNullable(title)
					   .map(board.title.value::contains)
					   .orElse(null);
	}

	public BooleanExpression contentContains(String content) {
		return Optional.ofNullable(content)
					   .map(board.content.value::contains)
					   .orElse(null);
	}

	public BooleanExpression createdAtGoe(LocalDateTime dateTimeFrom) {
		return Optional.ofNullable(dateTimeFrom)
					   .map(board.createdAt::goe)
					   .orElse(null);
	}

	public BooleanExpression createdAtLoe(LocalDateTime dateTimeTo) {
		return Optional.ofNullable(dateTimeTo)
					   .map(board.createdAt::loe)
					   .orElse(null);
	}

}
