package com.example.springboot_jpa.user.controller.request;

import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원 도메인에 사용되는 요청 형식")
public record UserRequest(
		@Schema(example = "방가맨")
		@NotBlank(message = Nickname.NICKNAME_BLANK_MESSAGE)
		@Size(max = Nickname.NICKNAME_MAX_LENGTH,
			  message = Nickname.NICKNAME_LENGTH_MESSAGE
		)
		String nickname
) {

	public User toUser() {
		return User.create(
				Nickname.of(nickname)
		);
	}

}
