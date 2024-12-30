package com.example.springboot_jpa.comment.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.service.BoardService;
import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.repository.CommentRepository;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final BoardService boardService;

	private final UserService userService;

	private final CommentRepository commentRepository;

	@Transactional
	public void post(Comment comment, Long boardId, Long userId) {
		// 게시글 정보 찾기, 댓글 카운트 추가
		Board board = boardService.findById(boardId);
		board.addCommentCount();

		// 사용자 정보 찾기
		User user = userService.findById(userId);

		comment.updateBoard(board);
		comment.updateUser(user);

		commentRepository.save(comment);
	}

}
