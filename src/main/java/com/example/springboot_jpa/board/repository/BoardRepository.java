package com.example.springboot_jpa.board.repository;

import com.example.springboot_jpa.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findAllByUserId(Long userId, Pageable pageable);

	Page<Board> findOneByTitle_ValueContaining(String title, Pageable pageable);

	Page<Board> findOneByContent_ValueContaining(String content, Pageable pageable);

}
