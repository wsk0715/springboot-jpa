package com.example.springboot_jpa.board.controller;

import com.example.springboot_jpa.board.controller.request.BoardRequest;
import com.example.springboot_jpa.board.controller.response.BoardResponse;
import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.service.BoardService;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.util.JwtCookieUtil;
import com.example.springboot_jpa.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController implements BoardControllerDocs {

	private final BoardService boardService;

	private final JwtCookieUtil jwtCookieUtil;

	private final JwtTokenUtil jwtTokenUtil;


	@GetMapping
	public ResponseEntity<BaseResponse> getBoards() {
		List<Board> boards = boardService.getBoards();
		List<BoardResponse> data = BoardResponse.createList(boards);

		BaseResponse res = BaseResponse.ok()
									   .title("게시글 목록 불러오기")
									   .description("게시글 목록을 불러왔습니다.")
									   .data(data)
									   .build();

		return ResponseEntity.ok(res);
	}

	@GetMapping("/{boardId}")
	public ResponseEntity<BaseResponse> getBoard(@PathVariable Long boardId) {
		Board board = boardService.getBoard(boardId);
		BoardResponse data = BoardResponse.create(board);

		BaseResponse res = BaseResponse.ok()
									   .title("게시글 불러오기")
									   .description("게시글을 불러왔습니다.")
									   .data(data)
									   .build();

		return ResponseEntity.ok(res);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> post(@RequestBody BoardRequest boardRequest,
											 HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		Board board = boardRequest.toBoard();

		boardService.post(token, board);

		BaseResponse res = BaseResponse.ok()
									   .title("게시글 작성 완료")
									   .description("게시글을 작성했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

	@PatchMapping("/{boardId}")
	public ResponseEntity<BaseResponse> updateBoard(@PathVariable Long boardId,
													@RequestBody BoardRequest boardRequest,
													HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		Long userId = jwtTokenUtil.getUserId(token);

		Board board = boardRequest.toBoard();
		boardService.updateBoard(boardId, board, userId);

		BaseResponse res = BaseResponse.ok()
									   .title("게시글 수정 완료")
									   .description("게시글을 수정했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<BaseResponse> deleteBoard(@PathVariable Long boardId,
													HttpServletRequest request
												   ) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		Long userId = jwtTokenUtil.getUserId(token);

		boardService.delete(boardId, userId);

		BaseResponse res = BaseResponse.ok()
									   .title("게시글 삭제 완료")
									   .description("게시글을 삭제했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

}
