package com.example.springboot_jpa.comment.controller.response;

import com.example.springboot_jpa.comment.domain.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(
		Long id,
		String content,
		Long boardId,
		Long userId,
		String userNickname,
		LocalDateTime created_at,
		LocalDateTime updated_at
) {

	public static List<CommentResponse> from(List<Comment> comments) {
		return comments.stream()
					   .map(CommentResponse::from)
					   .collect(Collectors.toList());
	}

	public static CommentResponse from(Comment comment) {
		return new CommentResponse(
				comment.getId(),
				comment.getContent().getValue(),
				comment.getBoard().getId(),
				comment.getUser().getId(),
				comment.getUser().getNickname().getValue(),
				comment.getCreatedAt(),
				comment.getUpdatedAt()
		);
	}

}
