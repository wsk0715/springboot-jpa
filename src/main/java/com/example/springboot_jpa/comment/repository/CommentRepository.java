package com.example.springboot_jpa.comment.repository;

import com.example.springboot_jpa.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
