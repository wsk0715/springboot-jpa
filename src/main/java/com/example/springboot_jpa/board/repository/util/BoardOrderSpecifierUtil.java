package com.example.springboot_jpa.board.repository.util;

import com.example.springboot_jpa.board.domain.Board;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class BoardOrderSpecifierUtil {

	public static OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
		List<OrderSpecifier<?>> orders = new ArrayList<>();

		for (Order o : sort) {
			PathBuilder<Board> pathBuilder = new PathBuilder<>(Board.class, "board");
			orders.add(o.isAscending()
					   ? pathBuilder.getString(o.getProperty()).asc()
					   : pathBuilder.getString(o.getProperty()).desc());
		}

		return orders.toArray(new OrderSpecifier[0]);
	}

}
