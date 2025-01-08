package com.example.springboot_jpa.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.oauth.google")
public record OAuthGoogleProperties(
		String authUrl,
		String clientId,
		String redirectUri
) {

	public String getUrl() {
		return authUrl
			   + "?client_id=" + clientId
			   + "&redirect_uri=" + redirectUri
			   + "&scope=openid%20profile%20email"
			   + "&response_type=code";
	}

}
