package com.example.springboot_jpa.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.oauth.google")
public record OAuthGoogleProperties(
		String clientId,
		String clientSecret,
		String redirectUri,
		String authUrl,
		String tokenUrl,
		String userInfoUrl
) {

	public String getUrl() {
		return authUrl
			   + "?client_id=" + clientId
			   + "&redirect_uri=" + redirectUri
			   + "&scope=openid%20profile%20email"
			   + "&response_type=code";
	}

	public String getBody(String code) {
		return "code=" + code +
			   "&client_id=" + clientId +
			   "&client_secret=" + clientSecret +
			   "&redirect_uri=" + redirectUri +
			   "&grant_type=authorization_code";
	}

}
