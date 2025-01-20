package com.example.springboot_jpa.auth.controller.request;

import com.example.springboot_jpa.auth.domain.Auth;
import com.example.springboot_jpa.auth.domain.vo.AuthLoginId;
import com.example.springboot_jpa.auth.domain.vo.AuthPassword;

public record AuthRequest(
		String loginId,
		String password
) {

	public Auth toAuth() {
		return Auth.create(
				AuthLoginId.of(loginId()),
				AuthPassword.of(password())
		);
	}

}
