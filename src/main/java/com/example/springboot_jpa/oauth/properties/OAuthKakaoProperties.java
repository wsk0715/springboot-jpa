package com.example.springboot_jpa.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.oauth.kakao")
public record OAuthKakaoProperties(
		String authUrl,
		String clientId,
		String redirectUri
) {

	public String getUrl() {
		return authUrl
			   + "?client_id=" + clientId
			   + "&redirect_uri=" + redirectUri
			   + "&response_type=code";
	}

}
