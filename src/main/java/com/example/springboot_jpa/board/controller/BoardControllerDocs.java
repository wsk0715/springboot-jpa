package com.example.springboot_jpa.board.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.board.controller.request.BoardRequest;
import com.example.springboot_jpa.board.controller.response.BoardResponse;
import com.example.springboot_jpa.board.domain.dto.BoardResponseSummary;
import com.example.springboot_jpa.common.pagination.ResponsePage;
import com.example.springboot_jpa.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Board", description = "Board 도메인 관련 API")
public interface BoardControllerDocs {


	@Operation(summary = "게시글 작성", description = "게시글을 등록한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 작성 성공", responseCode = "200")
	})
	ResponseEntity<BoardResponse> postBoard(@RequestBody BoardRequest boardRequest,
											@Parameter(hidden = true) @LoginUser User user);

	@Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 목록 조회 성공", responseCode = "200")
	})
	@GetMapping
	ResponseEntity<ResponsePage<BoardResponseSummary>> getBoards(@RequestParam(required = false) Long userId,
																 @RequestParam(required = false) String userNickname,
																 @RequestParam(required = false) String title,
																 @RequestParam(defaultValue = "1") int page,
																 @RequestParam(defaultValue = "20") int size,
																 @RequestParam(defaultValue = "id, desc") String sort);

	@Operation(summary = "게시글 조회", description = "게시글을 조회한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 조회 성공", responseCode = "200")
	})
	@GetMapping("/{boardId}")
	ResponseEntity<BoardResponse> getBoard(@PathVariable Long boardId);

	@Operation(summary = "게시글 수정", description = "게시글을 수정한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 수정 성공", responseCode = "200")
	})
	@PatchMapping("/{boardId}")
	ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId,
											  @RequestBody BoardRequest boardRequest,
											  @Parameter(hidden = true) @LoginUser User loginUser);

	@Operation(summary = "게시글 삭제", description = "게시글을 삭제한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 삭제 성공", responseCode = "200")
	})
	@DeleteMapping("/{boardId}")
	ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,
									 @Parameter(hidden = true) @LoginUser User loginUser);

}
