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
		long totalElements
) {

	public static <T> ResponsePage<T> from(List<T> contents,
										   int currentPage,
										   int totalPage,
										   int currentElements,
										   long totalElements) {
		return new ResponsePage<>(
				contents, currentPage, totalPage, currentElements, totalElements
		);
	}

}

