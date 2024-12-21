package com.example.springboot_jpa.oauth.dto;

public record OAuthResult(
		boolean initialLogin,
		String token) {

}
