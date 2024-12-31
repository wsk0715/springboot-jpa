package com.example.springboot_jpa.comment.repository;

import com.example.springboot_jpa.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByBoardId(Long boardId);

}
