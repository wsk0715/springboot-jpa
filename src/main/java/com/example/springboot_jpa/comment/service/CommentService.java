package com.example.springboot_jpa.comment.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.service.BoardService;
import com.example.springboot_jpa.comment.domain.Comment;
import com.example.springboot_jpa.comment.repository.CommentRepository;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import com.example.springboot_jpa.exception.type.status4xx.ForbiddenException;
import com.example.springboot_jpa.exception.type.status4xx.NotFoundException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final BoardService boardService;

	private final UserService userService;

	private final CommentRepository commentRepository;


	public Page<Comment> getMany(Long boardId, Pageable pageable) {
		return commentRepository.findAllByBoardId(boardId, pageable);
	}

	@Transactional
	public Comment post(Comment comment, Long boardId, User user) {
		// 게시글 정보 찾기, 댓글 카운트 추가
		Board board = boardService.findById(boardId);
		board.addCommentCount();

		// 사용자 정보 찾기
		comment.updateBoard(board);
		comment.updateUser(user);

		return commentRepository.save(comment);
	}

	@Transactional
	public void delete(Long commentId, Long boardId, User user) {
		// 댓글 찾기
		Comment comment = commentRepository.findById(commentId)
										   .orElseThrow(() -> new SpringbootJpaException("해당 댓글이 존재하지 않습니다."));

		// 작성자 비교
		User commentUser = comment.getUser();
		if (!user.getId().equals(commentUser.getId())) {
			throw new SpringbootJpaException("사용자 정보와 댓글 작성자가 일치하지 않습니다.");
		}

		// 댓글 삭제
		commentRepository.delete(comment);

		// 게시글 비교
		Board commentBoard = comment.getBoard();
		if (!boardId.equals(commentBoard.getId())) {
			throw new SpringbootJpaException("게시글 정보와 댓글이 일치하지 않습니다.");
		}

		// 댓글 카운트 차감
		commentBoard.subtractCommentCount();
	}

	@Transactional
	public Comment update(Long commentId, Comment comment, User loginUser) {
		// 사용자 확인
		Long loginUserId = loginUser.getId();

		// 댓글 작성자 비교
		Comment dbComment = commentRepository.findById(commentId)
											 .orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다."));
		if (!loginUserId.equals(dbComment.getUser().getId())) {
			throw new ForbiddenException("자기 자신의 댓글만 수정할 수 있습니다.");
		}

		// TODO: boardId도 비교할 것인가?

		dbComment.updateContent(comment.getContent());
		return dbComment;
	}

}
