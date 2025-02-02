package com.example.springboot_jpa.auth.domain.vo;

import com.example.springboot_jpa.common.encryption.BCryptEncryptUtil;
import com.example.springboot_jpa.exception.type.domain.ArgumentLengthException;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 회원 비밀번호에 관한 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class AuthPassword {

	public static final int PASSWORD_MIN_LENGTH = 10;
	public static final String LOGIN_PASSWORD_EMPTY_MESSAGE =
			"로그인 비밀번호를 입력해주세요.";
	public static final String LOGIN_PASSWORD_LENGTH_MESSAGE = String.format(
			"로그인 비밀번호는 %s자 이상으로 입력해주세요.", PASSWORD_MIN_LENGTH
	);

	
	@Column(name = "password", nullable = false)
	private String value;

	protected AuthPassword() {
	}

	private AuthPassword(String password) {
		System.out.println(password);
		validate(password);
		this.value = BCryptEncryptUtil.encode(password);
	}

	public static AuthPassword of(String password) {
		return new AuthPassword(password);
	}


	public boolean isMatches(String plainPassword) {
		if (BCryptEncryptUtil.matches(plainPassword, this.value)) {
			return true;
		}
		return false;
	}

	private void validate(String plainPassword) {
		if (plainPassword == null || plainPassword.isEmpty()) {
			throw new ArgumentNullException(LOGIN_PASSWORD_EMPTY_MESSAGE);
		}
		if (plainPassword.length() < PASSWORD_MIN_LENGTH) {
			throw new ArgumentLengthException(LOGIN_PASSWORD_LENGTH_MESSAGE);
		}
	}

	@Override
	public String toString() {
		return this.value;
	}

}
