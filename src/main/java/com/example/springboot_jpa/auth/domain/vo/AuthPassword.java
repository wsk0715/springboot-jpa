package com.example.springboot_jpa.auth.domain.vo;

import com.example.springboot_jpa.common.encryption.BCryptEncryptUtil;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * 회원 비밀번호 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class AuthPassword {

	public static final int PASSWORD_MIN_LENGTH = 10;

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
			throw new SpringbootJpaException("로그인 비밀번호를 입력해주세요.");
		}
		if (plainPassword.length() < PASSWORD_MIN_LENGTH) {
			throw new SpringbootJpaException("로그인 비밀번호는 " + PASSWORD_MIN_LENGTH + "자 이상으로 입력해주세요.");
		}
	}

	@Override
	public String toString() {
		return this.value;
	}

}
