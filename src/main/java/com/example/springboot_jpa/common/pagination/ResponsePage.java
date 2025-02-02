package com.example.springboot_jpa.common.pagination;

import java.util.List;

/**
 * 응답 페이지네이션에 사용되는 DTO
 */
public record ResponsePage<T>(
		List<T> contents,
		int currentPage,
		int totalPages,
		int currentElements,
		int totalElements
) {

	public static <T> ResponsePage<T> from(List<T> contents,
										   int currentPage,
										   int maxPage,
										   int currentElements,
										   int totalElements) {
		return new ResponsePage<>(contents, currentPage, maxPage, currentElements, totalElements);
	}

}

