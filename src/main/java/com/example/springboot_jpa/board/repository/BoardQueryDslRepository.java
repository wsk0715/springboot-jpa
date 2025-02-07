package com.example.springboot_jpa.board.repository;

import static com.example.springboot_jpa.board.domain.QBoard.board;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.dto.BoardSearchParams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

	public Page<Board> findBoardsByCondition(BoardSearchParams searchParams, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();

		// 사용자 ID 조건
		if (searchParams.userId() != null) {
			builder.and(board.user.id.eq(searchParams.userId()));
		}

		// 닉네임 조건
		if (searchParams.userNickname() != null) {
			builder.and(board.user.nickname.value.eq(searchParams.userNickname()));
		}

		// 제목/내용 검색 조건
		if (searchParams.titleOrContent() != null) {
			builder.and(board.title.value.contains(searchParams.titleOrContent())
										 .or(board.content.value.contains(searchParams.titleOrContent())));
		} else {
			if (searchParams.title() != null) {
				builder.and(board.title.value.contains(searchParams.title()));
			}
			if (searchParams.content() != null) {
				builder.and(board.content.value.contains(searchParams.content()));
			}
		}

		// 날짜 조건
		if (searchParams.dateTimeFrom() != null) {
			builder.and(board.createdAt.goe(searchParams.dateTimeFrom()));
		}
		if (searchParams.dateTimeTo() != null) {
			builder.and(board.createdAt.loe(searchParams.dateTimeTo()));
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
