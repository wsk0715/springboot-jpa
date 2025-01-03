package com.example.springboot_jpa.board.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.repository.BoardRepository;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	private final UserService userService;


	public Board findById(Long boardId) {
		return boardRepository.findById(boardId)
							  .orElseThrow(() -> new SpringbootJpaException("해당 게시글을 찾을 수 없습니다."));
	}

	public List<Board> getBoards() {
		return boardRepository.findAll();
	}

	public Board getBoard(Long boardId) {
		return findById(boardId);
	}

	@Transactional
	public void post(Board board, User user) {
		board.updateUser(user);

		boardRepository.save(board);
	}


	@Transactional
	public void updateBoard(Long boardId, Board board, User user) {
		Board dbBoard = findById(boardId);

		// 게시글 작성자 확인
		User boardUser = dbBoard.getUser();
		if (!user.getId().equals(boardUser.getId())) {
			throw new SpringbootJpaException("사용자 정보와 게시글 작성자가 일치하지 않습니다.");
		}

		// 게시글 업데이트
		String title = board.getTitle();
		String content = board.getContent();

		dbBoard.updateTitle(title);
		dbBoard.updateContent(content);
	}

	public void delete(Long boardId, User user) {
		Board board = findById(boardId);
		User boardUser = board.getUser();

		// 게시글 작성자 확인
		if (!user.getId().equals(boardUser.getId())) {
			throw new SpringbootJpaException("사용자 정보와 게시글 작성자와 일치하지 않습니다.");
		}

		boardRepository.delete(board);
	}

}
