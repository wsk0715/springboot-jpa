package com.example.springboot_jpa.auth.controller.request;

import com.example.springboot_jpa.auth.domain.vo.AuthPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "비밀번호 변경 요청 시에 사용되는 요청 형식")
public record PasswordChangeRequest(
		@Schema(example = "bangaman1@")
		@NotNull(message = AuthPassword.LOGIN_PASSWORD_BLANK_MESSAGE)
		@Size(min = AuthPassword.PASSWORD_MIN_LENGTH,
			  message = AuthPassword.LOGIN_PASSWORD_LENGTH_MESSAGE
		)
		String currentPassword,

		@Schema(example = "bangaman2@")
		@NotNull(message = AuthPassword.LOGIN_PASSWORD_BLANK_MESSAGE)
		@Size(min = AuthPassword.PASSWORD_MIN_LENGTH,
			  message = AuthPassword.LOGIN_PASSWORD_LENGTH_MESSAGE
		)
		String newPassword
) {

}
