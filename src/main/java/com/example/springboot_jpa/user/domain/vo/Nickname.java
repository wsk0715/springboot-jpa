package com.example.springboot_jpa.user.domain.vo;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;


/**
 * 사용자 닉네임에 관한 도메인 로직을 담은 VO
 */
@Getter
@Embeddable
public class Nickname {

	public static final int MAX_NICKNAME_LENGTH = 20;
	public static final Pattern NICKNAME_REGEX = Pattern.compile("^[가-힣0-9a-zA-Z._]+$");

	@Column(name = "nickname", nullable = false)
	private String value;

	protected Nickname() {
	}

	private Nickname(String nickname) {
		this.value = validateAndTrim(nickname);
	}

	public static Nickname of(String nickname) {
		return new Nickname(nickname);
	}


	private String validateAndTrim(String nickname) {
		if (nickname == null || nickname.isEmpty()) {
			throw new SpringbootJpaException("닉네임을 입력해주세요.");
		}
		String trimmedNickname = nickname.trim();
		if (trimmedNickname.length() > MAX_NICKNAME_LENGTH) {
			throw new SpringbootJpaException("닉네임은 " + MAX_NICKNAME_LENGTH + "자 이하로 입력해주세요.");
		}
		if (!NICKNAME_REGEX.matcher(trimmedNickname).matches()) {
			throw new SpringbootJpaException("닉네임 형식에 맞게 입력해주세요.");
		}
		return trimmedNickname;
	}

}
