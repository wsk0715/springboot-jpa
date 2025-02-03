package com.example.springboot_jpa.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;
import com.example.springboot_jpa.board.repository.BoardRepository;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import com.example.springboot_jpa.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Mock
	private BoardRepository boardRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private BoardService boardService;

	private Board board1, board2, board3;
	private User user1, user2;
	private LocalDateTime now;
	private Pageable pageable;

	@BeforeEach
	void setUp() {
		now = LocalDateTime.now();

		user1 = User.builder()
					.id(1L)
					.nickname(Nickname.of("user1"))
					.build();

		user2 = User.builder()
					.id(2L)
					.nickname(Nickname.of("user2"))
					.build();

		board1 = Board.builder()
					  .id(1L)
					  .title(BoardTitle.of("테스트 제목1"))
					  .content(BoardContent.of("테스트 내용1"))
					  .user(user1)
					  .build();
		ReflectionTestUtils.setField(board1, "createdAt", now.minusDays(5));

		board2 = Board.builder()
					  .id(2L)
					  .title(BoardTitle.of("테스트 제목2"))
					  .content(BoardContent.of("다른 내용2"))
					  .user(user1)
					  .build();
		ReflectionTestUtils.setField(board2, "createdAt", now.minusDays(3));

		board3 = Board.builder()
					  .id(3L)
					  .title(BoardTitle.of("다른 제목3"))
					  .content(BoardContent.of("테스트 내용3"))
					  .user(user2)
					  .build();
		ReflectionTestUtils.setField(board3, "createdAt", now.minusDays(1));

		pageable = PageRequest.of(0, 20);
	}

	@Nested
	@DisplayName("단일 조건 검색")
	class WithNoDateCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getAllBoards() {
			// given
			List<Board> boards = Arrays.asList(board1, board2, board3);
			Page<Board> boardPage = new PageImpl<>(boards, pageable, boards.size());
			when(boardRepository.findAll(pageable)).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, null, null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(3);
			assertThat(result.getContent()).contains(board1, board2, board3);
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserId() {
			// given
			List<Board> userBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(userBoards, pageable, userBoards.size());
			when(boardRepository.findByUserId(eq(1L), any(Pageable.class))).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(1L, null, null, null, null, null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNickname() {
			// given
			List<Board> userBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(userBoards, pageable, userBoards.size());
			when(userService.findUserByNickname("user1")).thenReturn(user1);
			when(boardRepository.findByUserId(eq(1L), any(Pageable.class))).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, "user1", null, null, null, null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitle() {
			// given
			List<Board> titleBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(titleBoards, pageable, titleBoards.size());
			when(boardRepository.findByTitle_ValueContaining(eq("테스트 제목"), any(Pageable.class))).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, "테스트 제목", null, null, null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContent() {
			// given
			List<Board> contentBoards = Arrays.asList(board1, board3);
			Page<Board> boardPage = new PageImpl<>(contentBoards, pageable, contentBoards.size());
			when(boardRepository.findByContent_ValueContaining(eq("테스트 내용"), any(Pageable.class))).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, "테스트 내용", null, null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board3);
			assertThat(result.getContent()).doesNotContain(board2);
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContent() {
			// given
			List<Board> matchingBoards = Arrays.asList(board1, board2, board3);
			Page<Board> boardPage = new PageImpl<>(matchingBoards, pageable, matchingBoards.size());
			when(boardRepository.findByTitle_ValueContainingOrContent_ValueContaining(
					eq("테스트"), eq("테스트"), any(Pageable.class))).thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, "테스트", null, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(3);
			assertThat(result.getContent()).contains(board1, board2, board3);
		}

	}


	@Nested
	@DisplayName("날짜 시작 조건이 있는 경우")
	class WithDateFromCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> afterDateBoards = Arrays.asList(board3);
			Page<Board> boardPage = new PageImpl<>(afterDateBoards, pageable, afterDateBoards.size());
			when(boardRepository.findByCreatedAtAfter(eq(fromDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, null, fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board3);
			assertThat(result.getContent()).doesNotContain(board1, board2);
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(4);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> filteredBoards = Arrays.asList(board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByUserIdAndCreatedAtAfter(
					eq(1L), eq(fromDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(1L, null, null, null, null, fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board2);
			assertThat(result.getContent()).doesNotContain(board1, board3);
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(4);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> filteredBoards = Arrays.asList(board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(userService.findUserByNickname("user1")).thenReturn(user1);
			when(boardRepository.findByUserIdAndCreatedAtAfter(
					eq(1L), eq(fromDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, "user1", null, null, null, fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board2);
			assertThat(result.getContent()).doesNotContain(board1, board3);
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(4);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> filteredBoards = Arrays.asList(board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtAfter(
					eq("테스트 제목2"), eq(fromDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, "테스트 제목2", null, null, fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board2);
			assertThat(result.getContent()).doesNotContain(board1, board3);
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> filteredBoards = Arrays.asList(board3);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByContent_ValueContainingAndCreatedAtAfter(
					eq("테스트 내용3"), eq(fromDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, "테스트 내용3", null, fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board3);
			assertThat(result.getContent()).doesNotContain(board1, board2);
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndStartDate() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(4);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();

			List<Board> filteredBoards = Arrays.asList(board2, board3);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtAfterOrContent_ValueContainingAndCreatedAtAfter(
					eq("테스트"), eq(fromDateTime),
					eq("테스트"), eq(fromDateTime),
					any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, "테스트", fromDate, null, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board2, board3);
			assertThat(result.getContent()).doesNotContain(board1);
		}

	}


	@Nested
	@DisplayName("날짜 종료 조건이 있는 경우")
	class WithDateToCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(4);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> beforeDateBoards = Arrays.asList(board1);
			Page<Board> boardPage = new PageImpl<>(beforeDateBoards, pageable, beforeDateBoards.size());
			when(boardRepository.findByCreatedAtBefore(eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, null, null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board1);
			assertThat(result.getContent()).doesNotContain(board2, board3);
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByUserIdAndCreatedAtBefore(
					eq(1L), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(1L, null, null, null, null, null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(userService.findUserByNickname("user1")).thenReturn(user1);
			when(boardRepository.findByUserIdAndCreatedAtBefore(
					eq(1L), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, "user1", null, null, null, null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtBefore(
					eq("테스트 제목"), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, "테스트 제목", null, null, null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByContent_ValueContainingAndCreatedAtBefore(
					eq("테스트 내용1"), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, "테스트 내용1", null, null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board1);
			assertThat(result.getContent()).doesNotContain(board2, board3);
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndEndDate() {
			// given
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtBeforeOrContent_ValueContainingAndCreatedAtBefore(
					eq("테스트"), eq(toDateTime),
					eq("테스트"), eq(toDateTime),
					any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, "테스트", null, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

	}

	@Nested
	@DisplayName("날짜 시작, 종료 조건이 있는 경우")
	class WithDateFromAndToCondition {

		@Test
		@DisplayName("모든 게시글 조회")
		void getBoardsByDateRange() {

			// given
			LocalDate fromDate = LocalDate.now().minusDays(4);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> dateRangeBoards = Arrays.asList(board2);
			Page<Board> boardPage = new PageImpl<>(dateRangeBoards, pageable, dateRangeBoards.size());
			when(boardRepository.findByCreatedAtBetween(eq(fromDateTime), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, null, fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board2);
			assertThat(result.getContent()).doesNotContain(board1, board3);
		}

		@Test
		@DisplayName("사용자 ID로 게시글 조회")
		void getBoardsByUserIdAndDateRange() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(6);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByUserIdAndCreatedAtBetween(
					eq(1L), eq(fromDateTime), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(1L, null, null, null, null, fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("사용자 닉네임으로 게시글 조회")
		void getBoardsByUserNicknameAndDateRange() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(6);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(userService.findUserByNickname("user1")).thenReturn(user1);
			when(boardRepository.findByUserIdAndCreatedAtBetween(
					eq(1L), eq(fromDateTime), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, "user1", null, null, null, fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

		@Test
		@DisplayName("제목으로 게시글 조회")
		void getBoardsByTitleAndDateRange() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(6);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtBetween(
					eq("테스트 제목1"), eq(fromDateTime), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, "테스트 제목1", null, null, fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board1);
			assertThat(result.getContent()).doesNotContain(board2, board3);
		}

		@Test
		@DisplayName("내용으로 게시글 조회")
		void getBoardsByContentAndDateRange() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(6);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByContent_ValueContainingAndCreatedAtBetween(
					eq("테스트 내용1"), eq(fromDateTime), eq(toDateTime), any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, "테스트 내용1", null, fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(1);
			assertThat(result.getContent()).contains(board1);
			assertThat(result.getContent()).doesNotContain(board2, board3);
		}

		@Test
		@DisplayName("제목 또는 내용으로 게시글 조회")
		void getBoardsByTitleOrContentAndDateRange() {
			// given
			LocalDate fromDate = LocalDate.now().minusDays(6);
			LocalDate toDate = LocalDate.now().minusDays(2);
			LocalDateTime fromDateTime = fromDate.atStartOfDay();
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);

			List<Board> filteredBoards = Arrays.asList(board1, board2);
			Page<Board> boardPage = new PageImpl<>(filteredBoards, pageable, filteredBoards.size());
			when(boardRepository.findByTitle_ValueContainingAndCreatedAtBetweenOrContent_ValueContainingAndCreatedAtBetween(
					eq("테스트"), eq(fromDateTime), eq(toDateTime),
					eq("테스트"), eq(fromDateTime), eq(toDateTime),
					any(Pageable.class)))
					.thenReturn(boardPage);

			// when
			Page<Board> result = boardService.getBoards(null, null, null, null, "테스트", fromDate, toDate, pageable);

			// then
			assertThat(result.getContent()).hasSize(2);
			assertThat(result.getContent()).contains(board1, board2);
			assertThat(result.getContent()).doesNotContain(board3);
		}

	}

}
