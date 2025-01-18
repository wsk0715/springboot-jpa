package com.example.springboot_jpa.user.contoller.request;

import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 도메인에 대한 요청 형식")
public record UserRequest(
		Nickname nickname
) {

	public User toUser() {
		return User.create(nickname);
	}

}
