package com.example.springboot_jpa.comment.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.comment.controller.request.CommentRequest;
import com.example.springboot_jpa.comment.controller.response.CommentResponse;
import com.example.springboot_jpa.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment", description = "Comment 도메인 관련 API")
public interface CommentControllerDocs {

	@Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "댓글 등록 성공", responseCode = "200"),
	})
	ResponseEntity<Long> postComment(@PathVariable Long boardId,
									 @RequestBody CommentRequest commentRequest,
									 @Parameter(hidden = true) @LoginUser User user);

	@Operation(summary = "댓글 목록 조회", description = "게시글의 댓글 목록을 조회한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "댓글 목록 조회 성공", responseCode = "200"),
	})
	@GetMapping
	ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long boardId);


	@Operation(summary = "댓글 삭제", description = "게시글의 댓글을 삭제한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "댓글 삭제 성공", responseCode = "200"),
	})
	@DeleteMapping("/{commentId}")
	ResponseEntity<Void> deleteComment(@PathVariable Long boardId,
									   @PathVariable Long commentId,
									   @Parameter(hidden = true) @LoginUser User loginUser);

}
