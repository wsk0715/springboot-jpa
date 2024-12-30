package com.example.springboot_jpa.board.controller.request;

import com.example.springboot_jpa.board.domain.Board;

public record BoardRequest(
		String title,
		String content
) {

	public Board toBoard() {
		return Board.create(title, content);
	}

}
