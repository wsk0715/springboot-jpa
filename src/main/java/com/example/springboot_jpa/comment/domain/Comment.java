package com.example.springboot_jpa.comment.domain;

import com.example.springboot_jpa.board.domain.Board;
import com.example.springboot_jpa.config.domain.BaseEntity;
import com.example.springboot_jpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isDeleted = false;


	public void updateBoard(Board board) {
		this.board = board;
	}

	public void updateUser(User user) {
		this.user = user;
	}

}
