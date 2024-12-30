package com.example.springboot_jpa.user.domain;

import com.example.springboot_jpa.config.domain.BaseEntity;
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
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "user")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Builder
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	@Column(nullable = false, unique = true, length = 10)
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
		this.nickname = nickname;
	}

}
