package com.example.springboot_jpa.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Nested
	@DisplayName("단일 조건 검색")
	class WithNoDateCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getAllBoards() {
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserId() {
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNickname() {
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitle() {
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContent() {
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContent() {
		}

	}


	@Nested
	@DisplayName("날짜 시작 조건이 있는 경우")
	class WithDateFromCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByStartDate() {
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndStartDate() {
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndStartDate() {
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndStartDate() {
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndStartDate() {
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndStartDate() {
		}

	}


	@Nested
	@DisplayName("날짜 종료 조건이 있는 경우")
	class WithDateToCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByEndDate() {
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndEndDate() {
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndEndDate() {
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndEndDate() {
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndEndDate() {
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndEndDate() {
		}

	}

	@Nested
	@DisplayName("날짜 시작, 종료 조건이 있는 경우")
	class WithDateFromAndToCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByDateRange() {
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndDateRange() {
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndDateRange() {
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndDateRange() {
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndDateRange() {
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndDateRange() {
		}

	}

}
