package com.example.springboot_jpa.board.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;
import com.example.springboot_jpa.board.repository.BoardRepository;
import com.example.springboot_jpa.exception.type.status4xx.ForbiddenException;
import com.example.springboot_jpa.exception.type.status4xx.NotFoundException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

	public Page<Board> getBoards(Long userId,
								 String userNickname,
								 String title,
								 String content,
								 String titleOrContent,
								 LocalDate dateFrom,
								 LocalDate dateTo,
								 Pageable pageable) {
		LocalDateTime dateTimeFrom = dateFrom == null ? null : dateFrom.atTime(0, 0, 0);
		LocalDateTime dateTimeTo = dateTo == null ? null : dateTo.atTime(23, 59, 59, 999_999_999);

		System.out.println("dateTimeFrom = " + dateTimeFrom);
		System.out.println("dateTimeTo = " + dateTimeTo);

		if (dateTimeFrom != null && dateTimeTo != null) {
			if (userId != null) {
				return boardRepository.findByUserIdAndCreatedAtBetween(userId, dateTimeFrom, dateTimeTo, pageable);
			}
			if (userNickname != null) {
				User user = userService.findUserByNickname(userNickname);
				return boardRepository.findByUserIdAndCreatedAtBetween(user.getId(), dateTimeFrom, dateTimeTo, pageable);
			}
			if (titleOrContent != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtBetweenOrContent_ValueContainingAndCreatedAtBetween(titleOrContent, dateTimeFrom, dateTimeTo, titleOrContent, dateTimeFrom, dateTimeTo, pageable);
			}
			if (title != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtBetween(title, dateTimeFrom, dateTimeTo, pageable);
			}
			if (content != null) {
				return boardRepository.findByContent_ValueContainingAndCreatedAtBetween(content, dateTimeFrom, dateTimeTo, pageable);
			}
			return boardRepository.findByCreatedAtBetween(dateTimeFrom, dateTimeTo, pageable);
		}

		if (dateTimeFrom != null) {
			if (userId != null) {
				return boardRepository.findByUserIdAndCreatedAtAfter(userId, dateTimeFrom, pageable);
			}
			if (userNickname != null) {
				User user = userService.findUserByNickname(userNickname);
				return boardRepository.findByUserIdAndCreatedAtAfter(user.getId(), dateTimeFrom, pageable);
			}
			if (titleOrContent != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtAfterOrContent_ValueContainingAndCreatedAtAfter(titleOrContent, dateTimeFrom, titleOrContent, dateTimeFrom, pageable);
			}
			if (title != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtAfter(title, dateTimeFrom, pageable);
			}
			if (content != null) {
				return boardRepository.findByContent_ValueContainingAndCreatedAtAfter(content, dateTimeFrom, pageable);
			}
			return boardRepository.findByCreatedAtAfter(dateTimeFrom, pageable);
		}

		if (dateTimeTo != null) {
			if (userId != null) {
				return boardRepository.findByUserIdAndCreatedAtBefore(userId, dateTimeTo, pageable);
			}
			if (userNickname != null) {
				User user = userService.findUserByNickname(userNickname);
				return boardRepository.findByUserIdAndCreatedAtBefore(user.getId(), dateTimeTo, pageable);
			}
			if (titleOrContent != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtBeforeOrContent_ValueContainingAndCreatedAtBefore(titleOrContent, dateTimeTo, titleOrContent, dateTimeTo, pageable);
			}
			if (title != null) {
				return boardRepository.findByTitle_ValueContainingAndCreatedAtBefore(title, dateTimeTo, pageable);
			}
			if (content != null) {
				return boardRepository.findByContent_ValueContainingAndCreatedAtBefore(content, dateTimeTo, pageable);
			}
			return boardRepository.findByCreatedAtBefore(dateTimeTo, pageable);
		}

		if (userId != null) {
			return boardRepository.findByUserId(userId, pageable);
		}
		if (userNickname != null) {
			User user = userService.findUserByNickname(userNickname);
			return boardRepository.findByUserId(user.getId(), pageable);
		}
		if (titleOrContent != null) {
			return boardRepository.findByTitle_ValueContainingOrContent_ValueContaining(titleOrContent, titleOrContent, pageable);
		}
		if (title != null) {
			return boardRepository.findByTitle_ValueContaining(title, pageable);
		}
		if (content != null) {
			return boardRepository.findByContent_ValueContaining(content, pageable);
		}
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
