package com.example.springboot_jpa.oauth.constants;

import lombok.Getter;

@Getter
public enum OAuthProvider {
	GOOGLE("google", "sub"),
	KAKAO("kakao", "id");


	private final String name;

	private final String identifier;

	OAuthProvider(String name, String identifier) {
		this.name = name;
		this.identifier = identifier;
	}
}
