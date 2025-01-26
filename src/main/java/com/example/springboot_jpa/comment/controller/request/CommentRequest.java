package com.example.springboot_jpa.comment.controller.request;

import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.domain.vo.CommentContent;

public record CommentRequest(
		String content
) {

	public Comment toComment() {
		return Comment.create(
				CommentContent.of(content)
		);
	}

}
