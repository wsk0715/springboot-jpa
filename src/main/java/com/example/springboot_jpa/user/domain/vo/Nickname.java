package com.example.springboot_jpa.user.domain.vo;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
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

	private static final Pattern NICKNAME_REGEX = Pattern.compile("^[ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z._]+$");

	@Column(name = "nickname", nullable = false, length = 20)
	private String value;

	protected Nickname() {
	}

	public Nickname(String nickname) {
		validate(nickname);
		this.value = nickname;
	}

	public static Nickname of(String nickname) {
		return new Nickname(nickname);
	}


	private void validate(String nickname) {
		if (nickname == null || nickname.isEmpty()) {
			throw new SpringbootJpaException("닉네임을 입력해주세요.");
		}
		String trimmedNickname = nickname.trim();
		if (!NICKNAME_REGEX.matcher(nickname).matches()) {
			throw new SpringbootJpaException("닉네임 형식에 맞지 않습니다.");
		}
	}

}
