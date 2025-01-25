package com.example.springboot_jpa.auth.controller.response;

import com.example.springboot_jpa.auth.domain.Auth;
import java.time.LocalDateTime;

public record AuthResponse(
		String loginId,
		Long userId,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {

	public static AuthResponse from(Auth auth) {
		return new AuthResponse(auth.getLoginId().getValue(),
								auth.getUser().getId(),
								auth.getCreatedAt(),
								auth.getUpdatedAt());
	}

}
