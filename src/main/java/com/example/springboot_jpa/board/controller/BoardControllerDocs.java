package com.example.springboot_jpa.board.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.board.controller.request.BoardRequest;
import com.example.springboot_jpa.common.response.BaseResponse;
import com.example.springboot_jpa.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Board", description = "Board 도메인 관련 API")
public interface BoardControllerDocs {


	@Operation(summary = "게시글 작성", description = "새로운 게시글을 등록한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "게시글 작성 성공", responseCode = "200")
	})
	ResponseEntity<BaseResponse> postBoard(@RequestBody BoardRequest boardRequest, @LoginUser User user);

}
