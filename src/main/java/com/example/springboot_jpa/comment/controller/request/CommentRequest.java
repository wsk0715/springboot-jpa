package com.example.springboot_jpa.comment.controller.request;

import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.domain.vo.CommentContent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 도메인에 사용되는 요청 형식")
public record CommentRequest(
		@Schema(example = "저도 방가워요")
		@NotBlank(message = CommentContent.COMMENT_CONTENT_BLANK_MESSAGE)
		String content
) {

	public Comment toComment() {
		return Comment.create(
				CommentContent.of(content)
		);
	}

}
