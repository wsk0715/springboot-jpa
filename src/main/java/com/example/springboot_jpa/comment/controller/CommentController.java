package com.example.springboot_jpa.comment.controller;

import com.example.springboot_jpa.comment.controller.request.CommentRequest;
import com.example.springboot_jpa.comment.controller.response.CommentResponse;
import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.service.CommentService;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.util.JwtCookieUtil;
import com.example.springboot_jpa.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/{boardId}/comment")
public class CommentController implements CommentControllerDocs {

	private final CommentService commentService;

	private final JwtCookieUtil jwtCookieUtil;

	private final JwtTokenUtil jwtTokenUtil;


	@GetMapping
	public ResponseEntity<BaseResponse> getManyComment(@PathVariable Long boardId) {
		List<Comment> comments = commentService.getMany(boardId);
		List<CommentResponse> data = CommentResponse.createList(comments);
		
		BaseResponse res = BaseResponse.ok()
									   .title("댓글 조회 성공")
									   .description("댓글을 불러오는 데 성공했습니다.")
									   .data(data)
									   .build();

		return ResponseEntity.ok(res);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> post(@PathVariable Long boardId,
											 @RequestBody CommentRequest commentRequest,
											 HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		Long userId = jwtTokenUtil.getUserId(token);

		Comment comment = commentRequest.create();
		commentService.post(comment, boardId, userId);

		BaseResponse res = BaseResponse.ok()
									   .title("댓글 작성 완료")
									   .description("댓글을 작성했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<BaseResponse> delete(@PathVariable Long boardId,
											   @PathVariable Long commentId,
											   HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		Long userId = jwtTokenUtil.getUserId(token);

		commentService.delete(commentId, boardId, userId);

		BaseResponse res = BaseResponse.ok()
									   .title("댓글 삭제 성공")
									   .description("댓글을 삭제했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

}
