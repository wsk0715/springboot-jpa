package com.example.springboot_jpa.board.repository;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.dto.BoardSearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

	private final BoardJpaRepository boardJpaRepository;
	private final BoardQueryDslRepository boardQueryDslRepository;


	public Page<Board> findBoardsByCondition(BoardSearchParams searchParams,
											 Pageable pageable) {
		return boardQueryDslRepository.findBoardsByCondition(searchParams, pageable);
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
