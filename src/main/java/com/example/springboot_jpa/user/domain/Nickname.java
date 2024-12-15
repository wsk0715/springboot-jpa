package com.example.springboot_jpa.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Nickname {

	private String nickname;


	@Override
	public String toString() {
		return this.nickname;
	}

}
