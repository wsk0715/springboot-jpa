package com.example.springboot_jpa.board.controller.request;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 도메인에 사용되는 요청 형식")
public record BoardRequest(
		@Schema(example = "방가방가")
		@NotBlank(message = BoardTitle.BOARD_TITLE_BLANK_MESSAGE)
		@Size(max = BoardTitle.MAX_LENGTH,
			  message = BoardTitle.BOARD_TITLE_LENGTH_MESSAGE
		)
		String title,

		@Schema(example = "방가워요")
		@NotBlank(message = BoardContent.BOARD_CONTENT_BLANK_MESSAGE)
		String content
) {

	public Board toBoard() {
		return Board.create(
				BoardTitle.of(title),
				BoardContent.of(content)
		);
	}

}
