package com.example.springboot_jpa.board.domain;

import com.example.springboot_jpa.board.domain.vo.BoardContent;
import com.example.springboot_jpa.board.domain.vo.BoardTitle;
import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.exception.type.domain.ArgumentNullException;
import com.example.springboot_jpa.exception.type.domain.InvalidStateException;
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

/**
 * 데이터베이스의 board 테이블과 매칭되는 엔티티
 */
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

	private static final String BOARD_USER_NULL_MESSAGE = "게시글 작성자 정보를 입력해주세요.";
	private static final String BOARD_COMMENT_COUNT_MESSAGE = "게시글 댓글 수는 0보다 작을 수 없습니다.";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private BoardTitle title;

	@Embedded
	private BoardContent content;

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


	public static Board create(BoardTitle title, BoardContent content) {
		return Board.builder()
					.title(title)
					.content(content)
					.build();
	}

	public void updateTitle(BoardTitle title) {
		this.title = title;
	}

	public void updateContent(BoardContent content) {
		this.content = content;
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new ArgumentNullException(BOARD_USER_NULL_MESSAGE);
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
			throw new InvalidStateException(BOARD_COMMENT_COUNT_MESSAGE);
		}
		this.commentCount -= 1;
	}

}
