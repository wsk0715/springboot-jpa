package com.example.springboot_jpa.board.service;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.repository.BoardRepository;
import com.example.springboot_jpa.exception.SpringbootJpaException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	private final UserService userService;

	private final JwtTokenUtil jwtTokenUtil;


	public Board findById(Long boardId) {
		return boardRepository.findById(boardId)
							  .orElseThrow(() -> new SpringbootJpaException("해당 게시글을 찾을 수 없습니다."));
	}

	@Transactional
	public void post(String token, Board board) {
		Long userId = jwtTokenUtil.getUserId(token);
		User user = userService.findById(userId);

		board.updateUser(user);
		System.out.println(board);

		boardRepository.save(board);
	}

}
