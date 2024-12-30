package com.example.springboot_jpa.comment.controller;

import com.example.springboot_jpa.comment.controller.request.CommentRequest;
import com.example.springboot_jpa.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Comment", description = "Comment 도메인 관련 API")
public interface CommentControllerDocs {

	@Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "댓글 등록 성공", responseCode = "200"),
	})
	ResponseEntity<BaseResponse> post(Long id, CommentRequest commentRequest, HttpServletRequest request);

}
