package com.example.springboot_jpa.board.controller.request;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;

public record BoardRequest(
		String title,
		String content
) {

	public Board toBoard() {
		return Board.create(
				BoardTitle.of(title),
				BoardContent.of(content)
		);
	}

}
