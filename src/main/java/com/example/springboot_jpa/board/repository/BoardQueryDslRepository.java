package com.example.springboot_jpa.board.repository;

import static com.example.springboot_jpa.board.domain.QBoard.board;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public Page<Board> findBoardsByCondition(Long userId,
											 String userNickname,
											 String title,
											 String content,
											 String titleOrContent,
											 LocalDateTime dateTimeFrom,
											 LocalDateTime dateTimeTo,
											 Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();

		// 사용자 ID 조건
		if (userId != null) {
			builder.and(board.user.id.eq(userId));
		} else if (userNickname != null) {
			builder.and(board.user.nickname.eq(Nickname.of(userNickname)));
		}

		// 제목/내용 검색 조건
		if (titleOrContent != null) {
			builder.and(board.title.value.contains(titleOrContent).or(board.content.value.contains(titleOrContent)));
		} else {
			if (title != null) {
				builder.and(board.title.value.contains(title));
			}
			if (content != null) {
				builder.and(board.content.value.contains(content));
			}
		}

		// 날짜 조건
		if (dateTimeFrom != null) {
			builder.and(board.createdAt.goe(dateTimeFrom));
		}
		if (dateTimeTo != null) {
			builder.and(board.createdAt.loe(dateTimeTo));
		}

		// 쿼리 실행
		JPAQuery<Board> query = jpaQueryFactory
				.selectFrom(board)
				.where(builder);

		// 정렬 조건 적용
		for (Order o : pageable.getSort()) {
			PathBuilder<Board> pathBuilder = new PathBuilder<>(Board.class, "board");
			query.orderBy(o.isAscending() ? pathBuilder.getString(o.getProperty()).asc()
										  : pathBuilder.getString(o.getProperty()).desc());
		}

		// 페이징 적용
		query.offset(pageable.getOffset())
			 .limit(pageable.getPageSize());

		// 결과 및 카운트 쿼리 실행
		List<Board> result = query.fetch();
		long total = jpaQueryFactory
				.selectFrom(board)
				.where(builder)
				.fetchCount();

		return new PageImpl<>(result, pageable, total);
	}

}
