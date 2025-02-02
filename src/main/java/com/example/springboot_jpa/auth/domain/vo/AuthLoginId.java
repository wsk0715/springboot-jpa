package com.example.springboot_jpa.auth.domain.vo;

import com.example.springboot_jpa.exception.type.domain.ArgumentLengthException;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 회원 로그인 ID에 관한 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class AuthLoginId {

	public static final int LOGIN_ID_MIN_LENGTH = 8;
	public static final int LOGIN_ID_MAX_LENGTH = 20;
	public static final String LOGIN_ID_BLANK_MESSAGE =
			"로그인 아이디를 입력해주세요.";
	public static final String LOGIN_ID_LENGTH_MESSAGE =
			"로그인 아이디는 " + LOGIN_ID_MIN_LENGTH + "자 이상, " + LOGIN_ID_MAX_LENGTH + "자 이하로 입력해주세요.";


	@Column(name = "loginId", nullable = false)
	private String value;

	protected AuthLoginId() {
	}

	private AuthLoginId(String loginId) {
		validate(loginId);
		this.value = loginId;
	}

	public static AuthLoginId of(String loginId) {
		return new AuthLoginId(loginId);
	}


	private void validate(String loginId) {
		if (loginId == null || loginId.isEmpty()) {
			throw new ArgumentNullException(LOGIN_ID_BLANK_MESSAGE);
		}
		if (!checkLength(loginId)) {
			throw new ArgumentLengthException(LOGIN_ID_LENGTH_MESSAGE);
		}
	}

	private boolean checkLength(String loginId) {
		int l = loginId.length();
		if (l < LOGIN_ID_MIN_LENGTH) {
			return false;
		}
		if (l > LOGIN_ID_MAX_LENGTH) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
