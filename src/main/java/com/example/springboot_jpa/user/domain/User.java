package com.example.springboot_jpa.user.domain;

import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

/**
 * 데이터베이스의 user 테이블과 매칭되는 엔티티
 */
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET is_deleted = TRUE WHERE id = ?")
public class User extends BaseEntity {

	public static final String USER_NICKNAME_NULL_MESSAGE = "사용자 닉네임을 입력해주세요.";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Nickname nickname;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isDeleted = false;


	public static User create(Nickname nickname) {
		return User.builder()
				   .nickname(nickname)
				   .build();
	}

	public void updateNickname(Nickname nickname) {
		if (nickname == null) {
			throw new ArgumentNullException(USER_NICKNAME_NULL_MESSAGE);
		}
		this.nickname = nickname;
	}

}
