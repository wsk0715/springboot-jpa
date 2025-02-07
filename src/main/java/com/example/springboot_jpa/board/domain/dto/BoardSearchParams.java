package com.example.springboot_jpa.board.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BoardSearchParams(
		Long userId,
		String userNickname,
		String title,
		String content,
		String titleOrContent,
		LocalDateTime dateTimeFrom,
		LocalDateTime dateTimeTo
) {

}
