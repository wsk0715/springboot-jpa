package com.example.springboot_jpa.common.credential.manager.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.cookie")
public record CookieCredentialProperties(
		int maxAge,
		boolean isSecure,
		boolean httpOnly,
		String sameSite
) {

	public String cookieName() {
		return "jwt";
	}

}
