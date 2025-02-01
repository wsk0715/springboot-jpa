package com.example.springboot_jpa.board.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;
import com.example.springboot_jpa.board.repository.BoardRepository;
import com.example.springboot_jpa.exception.type.status4xx.ForbiddenException;
import com.example.springboot_jpa.exception.type.status4xx.NotFoundException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	private final UserService userService;


	public Board findById(Long boardId) {
		try {
			return boardRepository.findById(boardId)
								  .orElseThrow();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
	}

	public Page<Board> getBoards(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional
	public Board getBoard(Long boardId) {
		Board board = findById(boardId);
		board.addViewCount();
		return board;
	}

	@Transactional
	public Board post(Board board, User user) {
		board.updateUser(user);
		return boardRepository.save(board);
	}

	@Transactional
	public Board updateBoard(Long boardId, Board board, User user) {
		Board dbBoard = findById(boardId);

		// 게시글 작성자 확인
		User boardUser = dbBoard.getUser();
		if (!user.getId().equals(boardUser.getId())) {
			throw new ForbiddenException("사용자 정보와 게시글 작성자가 일치하지 않습니다.");
		}

		// 게시글 업데이트
		BoardTitle title = board.getTitle();
		BoardContent content = board.getContent();
		dbBoard.updateTitle(title);
		dbBoard.updateContent(content);

		return dbBoard;
	}

	public void delete(Long boardId, User user) {
		Board board = findById(boardId);
		User boardUser = board.getUser();

		// 게시글 작성자 확인
		if (!user.getId().equals(boardUser.getId())) {
			throw new ForbiddenException("사용자 정보와 게시글 작성자와 일치하지 않습니다.");
		}

		boardRepository.delete(board);
	}

}
