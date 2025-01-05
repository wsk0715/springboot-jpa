package com.example.springboot_jpa.board.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.board.controller.request.BoardRequest;
import com.example.springboot_jpa.board.controller.response.BoardResponse;
import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.service.BoardService;
import com.example.springboot_jpa.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController implements BoardControllerDocs {

	private final BoardService boardService;


	@Override
	@PostMapping
	public ResponseEntity<Long> postBoard(@RequestBody BoardRequest boardRequest,
										  @LoginUser User loginUser) {
		Board board = boardRequest.toBoard();
		Long boardId = boardService.post(board, loginUser);

		return ResponseEntity.ok(boardId);
	}

	@Override
	@GetMapping
	public ResponseEntity<List<BoardResponse>> getBoards() {
		List<Board> boards = boardService.getBoards();
		List<BoardResponse> res = BoardResponse.createList(boards);

		return ResponseEntity.ok(res);
	}

	@Override
	@GetMapping("/{boardId}")
	public ResponseEntity<BoardResponse> getBoard(@PathVariable Long boardId) {
		Board board = boardService.getBoard(boardId);
		BoardResponse res = BoardResponse.create(board);

		return ResponseEntity.ok(res);
	}

	@Override
	@PatchMapping("/{boardId}")
	public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId,
													 @RequestBody BoardRequest boardRequest,
													 @LoginUser User loginUser) {
		Board board = boardRequest.toBoard();
		boardService.updateBoard(boardId, board, loginUser);
		BoardResponse res = BoardResponse.create(board);

		return ResponseEntity.ok(res);
	}

	@Override
	@DeleteMapping("/{boardId}")
	public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,
											@LoginUser User loginUser) {
		boardService.delete(boardId, loginUser);

		return ResponseEntity.ok().build();
	}

}
