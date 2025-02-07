package com.example.springboot_jpa.board.repository;

import com.example.springboot_jpa.board.domain.Board;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

	private final BoardJpaRepository boardJpaRepository;
	private final BoardQueryDslRepository boardQueryDslRepository;


	public Page<Board> findBoardsByCondition(Long userId,
											 String userNickname,
											 String title,
											 String content,
											 String titleOrContent,
											 LocalDateTime dateTimeFrom,
											 LocalDateTime dateTimeTo,
											 Pageable pageable) {
		return boardQueryDslRepository.findBoardsByCondition(userId, userNickname, title, content, titleOrContent, dateTimeFrom, dateTimeTo, pageable);
	}

	public Board findById(Long boardId) {
		return boardJpaRepository.findById(boardId)
								 .orElseThrow();
	}

	public Board save(Board board) {
		return boardJpaRepository.save(board);
	}

	public void delete(Board board) {
		boardJpaRepository.delete(board);
	}

}
