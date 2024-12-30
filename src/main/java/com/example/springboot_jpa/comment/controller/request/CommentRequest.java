package com.example.springboot_jpa.comment.controller.request;

import com.example.springboot_jpa.comment.domain.Comment;

public record CommentRequest(
		String content
) {

	public Comment create() {
		return Comment.builder()
					  .content(content)
					  .build();
	}

}
