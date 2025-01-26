package com.example.springboot_jpa.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {


	public static Pageable createPageable(int page, int size, String sort) {
		String[] sortParams = sort.split(",");
		String sortBy = sortParams[0].trim();
		Sort.Direction sortDirection = sortParams.length > 1 && sortParams[1].trim().equalsIgnoreCase("asc")
									   ? Sort.Direction.ASC
									   : Sort.Direction.DESC;
		return PageRequest.of(page - 1, size, Sort.by(sortDirection, sortBy));
	}

}
