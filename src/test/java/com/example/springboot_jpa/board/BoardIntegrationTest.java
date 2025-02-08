package com.example.springboot_jpa.board;

import com.example.springboot_jpa.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BoardIntegrationTest extends BaseSpringBootTest {

	@Test
	@DisplayName("게시글 작성 요청이 성공한다.")
	void postBoard() {
	}

	@Test
	@DisplayName("유효하지 않은 게시글 작성 요청에 대해 400 상태코드를 반환한다.")
	void postBoard_invalidRequest() {
	}

	@Test
	@DisplayName("유효하지 않은 사용자의 게시글 작성 요청에 대해 401 상태코드를 반환한다.")
	void postBoard_invalidUser() {
	}

	@Test
	@DisplayName("게시글 목록 조회 요청이 성공한다.")
	void getBoards() {
	}

	@Test
	@DisplayName("게시글 조회 요청이 성공한다.")
	void getBoard() {
	}

	@Test
	@DisplayName("존재하지 않는 게시글 조회 요청에 대해 404 상태코드를 반환한다.")
	void getBoard_notFound() {
	}

	@Test
	@DisplayName("게시글 수정 요청이 성공하며, 수정된 게시글 내용을 응답한다.")
	void updateBoard() {
	}

	@Test
	@DisplayName("게시글 삭제 요청이 성공한다.")
	void deleteBoard() {
	}

}
