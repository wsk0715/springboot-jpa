package com.example.springboot_jpa.auth.domain;

import com.example.springboot_jpa.auth.domain.vo.AuthLoginId;
import com.example.springboot_jpa.auth.domain.vo.AuthPassword;
import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 데이터베이스의 auth 테이블과 매칭되는 엔티티
 */
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth")
public class Auth extends BaseEntity {

	public static final String AUTH_USER_NULL_MESSAGE = "인증 정보와 연결된 사용자 정보를 입력해주세요.";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private AuthLoginId loginId;

	@Embedded
	private AuthPassword password;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;


	public static Auth create(AuthLoginId loginId, AuthPassword password) {
		return Auth.builder()
				   .loginId(loginId)
				   .password(password)
				   .build();
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new ArgumentNullException(AUTH_USER_NULL_MESSAGE);
		}
		this.user = user;
	}

	public void updatePassword(AuthPassword password) {
		this.password = password;
	}

	public boolean comparePasswordWith(String plainPassword) {
		return this.getPassword().isMatches(plainPassword);
	}

}
