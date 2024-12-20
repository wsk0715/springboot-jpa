package com.example.springboot_jpa.response;

import com.example.springboot_jpa.response.constants.ResponseType;
import lombok.Getter;

@Getter
public class BaseResponse<T> {

	protected ResponseType responseType;
	protected String title;
	protected String description;
	protected T data;

}
