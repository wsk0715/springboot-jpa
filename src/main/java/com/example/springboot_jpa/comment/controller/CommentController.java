package com.example.springboot_jpa.comment.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.comment.controller.request.CommentRequest;
import com.example.springboot_jpa.comment.controller.response.CommentResponse;
import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.service.CommentService;
import com.example.springboot_jpa.common.util.PaginationUtil;
import com.example.springboot_jpa.user.domain.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/{boardId}/comment")
public class CommentController implements CommentControllerDocs {

	private final CommentService commentService;


	@Override
	@PostMapping
	public ResponseEntity<CommentResponse> postComment(@PathVariable Long boardId,
													   @Valid @RequestBody CommentRequest commentRequest,
													   @LoginUser User loginUser) {
		Comment comment = commentService.post(commentRequest.toComment(), boardId, loginUser);
		CommentResponse res = CommentResponse.from(comment);

		return ResponseEntity.ok(res);
	}

	@Override
	@GetMapping
	public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long boardId,
															 @RequestParam(defaultValue = "1") int page,
															 @RequestParam(defaultValue = "20") int size,
															 @RequestParam(defaultValue = "id, desc") String sort) {
		Pageable pageable = PaginationUtil.createPageable(page, size, sort);
		Page<Comment> comments = commentService.getMany(boardId, pageable);
		List<CommentResponse> res = CommentResponse.from(comments.getContent());

		return ResponseEntity.ok(res);
	}

	@Override
	@PatchMapping("/{commentId}")
	public ResponseEntity<CommentResponse> patchComment(@PathVariable Long boardId,
														@PathVariable Long commentId,
														@Valid @RequestBody CommentRequest commentRequest,
														@LoginUser User loginUser) {
		Comment comment = commentService.update(commentId, commentRequest.toComment(), loginUser);
		CommentResponse res = CommentResponse.from(comment);

		return ResponseEntity.ok(res);
	}

	@Override
	@DeleteMapping("/{commentId}")

	public ResponseEntity<Void> deleteComment(@PathVariable Long boardId,
											  @PathVariable Long commentId,
											  @LoginUser User loginUser) {
		commentService.delete(commentId, boardId, loginUser);

		return ResponseEntity.ok().build();
	}

}
