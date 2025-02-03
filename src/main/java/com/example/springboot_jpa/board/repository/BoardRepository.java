package com.example.springboot_jpa.board.repository;

import com.example.springboot_jpa.board.domain.Board;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findByCreatedAtBetween(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Pageable pageable);

	Page<Board> findByCreatedAtBefore(LocalDateTime dateTimeTo, Pageable pageable);

	Page<Board> findByCreatedAtAfter(LocalDateTime dateTimeFrom, Pageable pageable);


	Page<Board> findByUserId(Long userId, Pageable pageable);

	Page<Board> findByUserIdAndCreatedAtBetween(Long userId,
												LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
												Pageable pageable);

	Page<Board> findByUserIdAndCreatedAtBefore(Long userId, LocalDateTime dateTimeTo, Pageable pageable);

	Page<Board> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime dateTimeFrom, Pageable pageable);


	Page<Board> findByTitle_ValueContaining(String title, Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtBetween(String title,
															   LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
															   Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtBefore(String title,
															  LocalDateTime dateTimeTo,
															  Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtAfter(String title,
															 LocalDateTime dateTimeFrom,
															 Pageable pageable);


	Page<Board> findByContent_ValueContaining(String content, Pageable pageable);

	Page<Board> findByContent_ValueContainingAndCreatedAtBetween(String content,
																 LocalDateTime dateTimeFrom,
																 LocalDateTime dateTimeTo,
																 Pageable pageable);

	Page<Board> findByContent_ValueContainingAndCreatedAtBefore(String content,
																LocalDateTime dateTimeTo,
																Pageable pageable);

	Page<Board> findByContent_ValueContainingAndCreatedAtAfter(String content,
															   LocalDateTime dateTimeFrom,
															   Pageable pageable);


	Page<Board> findByTitle_ValueContainingOrContent_ValueContaining(String title, String content,
																	 Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtBetweenOrContent_ValueContainingAndCreatedAtBetween(String title,
																										   LocalDateTime timeFrom,
																										   LocalDateTime timeTo,
																										   String content,
																										   LocalDateTime dateTimeFrom,
																										   LocalDateTime dateTimeTo,
																										   Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtBeforeOrContent_ValueContainingAndCreatedAtBefore(String title,
																										 LocalDateTime timeTo,
																										 String content,
																										 LocalDateTime dateTimeTo,
																										 Pageable pageable);

	Page<Board> findByTitle_ValueContainingAndCreatedAtAfterOrContent_ValueContainingAndCreatedAtAfter(String title,
																									   LocalDateTime timeFrom,
																									   String content,
																									   LocalDateTime dateTimeFrom,
																									   Pageable pageable);

}
