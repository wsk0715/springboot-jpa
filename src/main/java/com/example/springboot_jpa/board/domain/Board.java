package com.example.springboot_jpa.board.domain;

import com.example.springboot_jpa.config.domain.BaseEntity;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "board")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE board SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Builder
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder.Default
	@Column(nullable = false)
	private Integer viewCount = 0;

	@Builder.Default
	@Column(nullable = false)
	private Integer likeCount = 0;

	@Builder.Default
	@Column(nullable = false)
	private Integer commentCount = 0;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isDeleted = false;


	public static Board create(String title, String content) {
		return Board.builder()
					.title(title)
					.content(content)
					.build();
	}

	public void updateUser(User user) {
		this.user = user;
	}

	public void addCommentCount() {
		this.commentCount += 1;
	}

	public void subtractCommentCount() {
		this.commentCount -= 1;
	}

}
