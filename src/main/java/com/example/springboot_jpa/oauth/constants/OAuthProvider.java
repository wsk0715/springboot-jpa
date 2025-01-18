package com.example.springboot_jpa.oauth.constants;

import lombok.Getter;

@Getter
public enum OAuthProvider {
	GOOGLE("google", "sub"),
	KAKAO("kakao", "id");


	private final String code;

	private final String identifier;

	OAuthProvider(String code, String identifier) {
		this.code = code;
		this.identifier = identifier;
	}

	public static OAuthProvider findByName(String name) {
		for (OAuthProvider provider : OAuthProvider.values()) {
			if (name.equals(provider.getCode())) {
				return provider;
			}
		}
		return null;
	}
}
