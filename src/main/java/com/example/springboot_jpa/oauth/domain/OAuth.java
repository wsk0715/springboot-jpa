package com.example.springboot_jpa.oauth.domain;

import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "oauth")
@Getter
@ToString
@NoArgsConstructor
public class OAuth extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OAuthProvider provider;

	@Column(nullable = false, unique = true, length = 255)
	private String code;  // 해싱된 OAuth 사용자 고유 id

	@Builder
	public OAuth(User user, OAuthProvider provider, String code) {
		this.user = user;
		this.provider = provider;
		this.code = code;
	}


	public static OAuth create(User user, OAuthProvider provider, String code) {
		return OAuth.builder()
					.user(user)
					.provider(provider)
					.code(code)
					.build();
	}

}
