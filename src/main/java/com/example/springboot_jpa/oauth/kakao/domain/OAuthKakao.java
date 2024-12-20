package com.example.springboot_jpa.oauth.kakao.domain;

import com.example.springboot_jpa.config.domain.BaseEntity;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_kakao")
@Getter
@NoArgsConstructor
public class OAuthKakao extends BaseEntity {

	@Id
	private Long userId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false, unique = true, length = 255)
	private String code;

	@Builder
	public OAuthKakao(Long userId, User user, String code) {
		this.userId = userId;
		this.user = user;
		this.code = code;
	}


	public static OAuthKakao create(Long userId, String code) {
		return OAuthKakao.builder()
						 .userId(userId)
						 .code(code)
						 .build();
	}

}
