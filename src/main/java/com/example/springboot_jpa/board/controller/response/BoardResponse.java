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

	public static List<BoardResponse> createList(List<Board> boards) {
		return boards.stream()
					 .map(BoardResponse::create)
					 .collect(Collectors.toList());
	}

	public static BoardResponse create(Board board) {
		return new BoardResponse(board.getId(),
								 board.getTitle().getValue(),
								 board.getContent().getValue(),
								 board.getUser().getId(),
								 board.getUser().getNickname().getNickname(),
								 board.getViewCount(),
								 board.getLikeCount(),
								 board.getCommentCount(),
								 board.getCreatedAt(),
								 board.getUpdatedAt());
	}

}
