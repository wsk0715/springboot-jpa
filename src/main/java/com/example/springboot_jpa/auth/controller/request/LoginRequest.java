package com.example.springboot_jpa.auth.controller.request;

import com.example.springboot_jpa.auth.domain.vo.AuthLoginId;
import com.example.springboot_jpa.auth.domain.vo.AuthPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "회원 로그인 요청 시에 사용되는 요청 형식")
public record LoginRequest(
		@Schema(example = "bangaman")
		@NotNull(message = AuthLoginId.LOGIN_ID_BLANK_MESSAGE)
		@Size(min = AuthLoginId.LOGIN_ID_MIN_LENGTH,
			  max = AuthLoginId.LOGIN_ID_MAX_LENGTH,
			  message = AuthLoginId.LOGIN_ID_LENGTH_MESSAGE
		)
		String loginId,

		@Schema(example = "bangaman1@")
		@NotNull(message = AuthPassword.LOGIN_PASSWORD_BLANK_MESSAGE)
		@Size(min = AuthPassword.PASSWORD_MIN_LENGTH,
			  message = AuthPassword.LOGIN_PASSWORD_LENGTH_MESSAGE
		)
		String password
) {

}
