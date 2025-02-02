package com.example.springboot_jpa.user.domain.vo;

import com.example.springboot_jpa.exception.type.domain.ArgumentFormatException;
import com.example.springboot_jpa.exception.type.domain.ArgumentLengthException;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;


/**
 * 사용자 닉네임에 관한 로직을 관리하는 VO
 */
@Getter
@Embeddable
public class Nickname {

	public static final int NICKNAME_MAX_LENGTH = 20;
	public static final Pattern NICKNAME_REGEX = Pattern.compile("^[가-힣0-9a-zA-Z._]+$");
	public static final String NICKNAME_BLANK_MESSAGE =
			"닉네임을 입력해주세요.";
	public static final String NICKNAME_LENGTH_MESSAGE =
			"닉네임은 " + NICKNAME_MAX_LENGTH + "자 이하로 입력해주세요.";
	public static final String NICKNAME_REGEX_MESSAGE =
			"닉네임 형식에 맞게 입력해주세요.";


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
			throw new ArgumentNullException(NICKNAME_BLANK_MESSAGE);
		}
		String trimmedNickname = nickname.trim();
		if (trimmedNickname.length() > NICKNAME_MAX_LENGTH) {
			throw new ArgumentLengthException(NICKNAME_LENGTH_MESSAGE);
		}
		if (!NICKNAME_REGEX.matcher(trimmedNickname).matches()) {
			throw new ArgumentFormatException(NICKNAME_REGEX_MESSAGE);
		}
		return trimmedNickname;
	}

}
