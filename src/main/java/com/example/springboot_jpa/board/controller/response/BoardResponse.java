package com.example.springboot_jpa.board.controller.response;

import com.example.springboot_jpa.board.domain.Board;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BoardResponse(
		Long id,
		String title,
		String content,
		Long userId,
		String userNickname,
		Integer viewCount,
		Integer likeCount,
		Integer commentCount,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {

	public static List<BoardResponse> from(List<Board> boards) {
		return boards.stream()
					 .map(BoardResponse::from)
					 .collect(Collectors.toList());
	}

	public static BoardResponse from(Board board) {
		return new BoardResponse(board.getId(),
								 board.getTitle().getValue(),
								 board.getContent().getValue(),
								 board.getUser().getId(),
								 board.getUser().getNickname().getValue(),
								 board.getViewCount(),
								 board.getLikeCount(),
								 board.getCommentCount(),
								 board.getCreatedAt(),
								 board.getUpdatedAt()
		);
	}

}
