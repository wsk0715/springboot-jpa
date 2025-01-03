package com.example.springboot_jpa.user.domain;

import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Nickname {

	private static final Pattern NICKNAME_REGEX = Pattern.compile("^[ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z._]+$");

	@Column(nullable = false, length = 20)
	private String nickname;

	public Nickname(String nickname) {
		System.out.println(nickname);
		String trimmedNickname = nickname.trim();
		validateRegex(trimmedNickname);
		this.nickname = trimmedNickname;
	}

	private void validateRegex(String nickname) {
		if (!NICKNAME_REGEX.matcher(nickname).matches()) {
			throw new SpringbootJpaException("닉네임 형식에 맞지 않습니다.");
		}
	}


	@Override
	public String toString() {
		return this.nickname;
	}

}
