package com.example.springboot_jpa.comment.domain;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.comment.domain.vo.CommentContent;
import com.example.springboot_jpa.common.domain.BaseEntity;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
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
 * 데이터베이스의 comment 테이블과 매칭되는 엔티티
 */
@Entity
@Table(name = "comment")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE comment SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private CommentContent content;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isDeleted = false;

	public static Comment create(CommentContent content) {
		return Comment.builder()
					  .content(content)
					  .build();
	}


	public void updateBoard(Board board) {
		if (board == null) {
			throw new SpringbootJpaException("댓글의 게시글 정보를 입력해주세요.");
		}
		this.board = board;
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new SpringbootJpaException("댓글의 작성자 정보를 입력해주세요.");
		}
		this.user = user;
	}

}
