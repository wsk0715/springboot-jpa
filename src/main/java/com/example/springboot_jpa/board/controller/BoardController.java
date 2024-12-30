package com.example.springboot_jpa.board.controller;

import com.example.springboot_jpa.board.controller.request.BoardRequest;
import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.service.BoardService;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.util.JwtCookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

}
