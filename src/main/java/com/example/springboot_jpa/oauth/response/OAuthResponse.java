package com.example.springboot_jpa.oauth.response;

import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.response.ResponseType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthResponse<T> extends BaseResponse<T> {

	private final String targetUrl;

	@Builder
	private OAuthResponse(ResponseType responseType, String title, String description, T data, String targetUrl) {
		this.responseType = responseType;
		this.title = title;
		this.description = description;
		this.data = data;
		this.targetUrl = targetUrl;
	}

	
	public static OAuthResponseBuilder ok() {
		return OAuthResponse.builder()
							.responseType(ResponseType.SUCCESS);
	}

	public static OAuthResponseBuilder redirect() {
		return OAuthResponse.builder()
							.responseType(ResponseType.REDIRECTION);
	}

}
