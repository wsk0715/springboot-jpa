package com.example.springboot_jpa.response;

import com.example.springboot_jpa.response.constants.ResponseType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse<T> {

	protected ResponseType responseType;
	protected String title;
	protected String description;
	protected T data;


	@Builder
	public BaseResponse(ResponseType responseType, String title, String description, T data) {
		this.responseType = responseType;
		this.title = title;
		this.description = description;
		this.data = data;
	}

	public static BaseResponseBuilder ok() {
		return BaseResponse.builder()
						   .responseType(ResponseType.SUCCESS);
	}

	public static BaseResponseBuilder redirect() {
		return BaseResponse.builder()
						   .responseType(ResponseType.REDIRECTION);
	}

}
