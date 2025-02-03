package com.example.springboot_jpa.board.domain.dto;

import com.example.springboot_jpa.board.domain.Board;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BoardResponseSummary(
		Long id,
		String title,
		Long userId,
		String userNickname,
		Integer viewCount,
		Integer likeCount,
		Integer commentCount,
		LocalDateTime createdAt) {

	public static List<BoardResponseSummary> from(List<Board> boards) {
		return boards.stream()
					 .map(BoardResponseSummary::from)
					 .collect(Collectors.toList());
	}

	public static BoardResponseSummary from(Board board) {
		return new BoardResponseSummary(board.getId(),
										board.getTitle().getValue(),
										board.getUser().getId(),
										board.getUser().getNickname().getValue(),
										board.getViewCount(),
										board.getLikeCount(),
										board.getCommentCount(),
										board.getCreatedAt()
		);
	}

}
