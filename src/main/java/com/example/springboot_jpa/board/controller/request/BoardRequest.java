package com.example.springboot_jpa.board.controller.request;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.Content;
import com.example.springboot_jpa.board.domain.vo.Title;

public record BoardRequest(
		String title,
		String content
) {

	public Board toBoard() {
		return Board.create(
				Title.of(title),
				Content.of(content)
		);
	}

}
