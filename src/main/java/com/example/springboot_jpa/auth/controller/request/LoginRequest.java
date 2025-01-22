package com.example.springboot_jpa.auth.controller.request;

public record LoginRequest(
		String loginId,
		String password
) {
	
}
