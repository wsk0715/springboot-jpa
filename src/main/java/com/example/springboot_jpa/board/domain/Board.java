package com.example.springboot_jpa.board.domain;

import com.example.springboot_jpa.board.domain.vo.Content;
import com.example.springboot_jpa.board.domain.vo.Title;
import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
@SQLDelete(sql = "UPDATE board SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Title title;

	@Embedded
	private Content content;

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


	public static Board create(Title title, Content content) {
		return Board.builder()
					.title(title)
					.content(content)
					.build();
	}

	public void updateTitle(Title title) {
		this.title = title;
	}

	public void updateContent(Content content) {
		this.content = content;
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new SpringbootJpaException("게시글 작성자 정보를 입력해주세요.");
		}
		this.user = user;
	}

	public void addViewCount() {
		this.viewCount += 1;
	}

	public void addCommentCount() {
		this.commentCount += 1;
	}

	public void subtractCommentCount() {
		if (commentCount == 0) {
			throw new SpringbootJpaException("게시글 댓글 수는 0보다 작을 수 없습니다.");
		}
		this.commentCount -= 1;
	}

}
