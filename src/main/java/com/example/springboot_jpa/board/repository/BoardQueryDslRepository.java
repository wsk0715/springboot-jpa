package com.example.springboot_jpa.board.repository;

import static com.example.springboot_jpa.board.domain.QBoard.board;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.board.domain.dto.BoardSearchParams;
import com.example.springboot_jpa.board.repository.querydsl.BoardExpressionProvider;
import com.example.springboot_jpa.board.repository.util.BoardOrderSpecifierUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final BoardExpressionProvider expressionProvider;


	public List<Board> findWithCondition(BoardSearchParams searchParams, Pageable pageable) {
		return jpaQueryFactory
				.selectFrom(board)
				.where(matchKeywords(searchParams))
				.orderBy(BoardOrderSpecifierUtil.getOrderSpecifier(pageable.getSort()))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
	}


	public Long countWithCondition(BoardSearchParams searchParams) {
		return jpaQueryFactory
				.select(Wildcard.count)
				.from(board)
				.where(matchKeywords(searchParams))
				.fetchOne();
	}

	private BooleanExpression[] matchKeywords(BoardSearchParams searchParams) {
		return new BooleanExpression[]{
				expressionProvider.userIdEq(searchParams.userId()),
				expressionProvider.userNicknameEq(searchParams.userNickname()),
				expressionProvider.titleOrContentContains(searchParams.titleOrContent(), searchParams.title(), searchParams.content()),
				expressionProvider.createdAtGoe(searchParams.dateTimeFrom()),
				expressionProvider.createdAtLoe(searchParams.dateTimeTo())
		};
	}

}
