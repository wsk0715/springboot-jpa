DROP TABLE IF EXISTS comment, board, oauth, user;
CREATE TABLE user
(
	id         BIGINT       NOT NULL AUTO_INCREMENT,
	nickname   VARCHAR(20)  NOT NULL,
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	is_deleted BIT(1)       NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE oauth
(
	id         BIGINT       NOT NULL AUTO_INCREMENT,
	user_id    BIGINT       NOT NULL,
	provider   VARCHAR(20)  NOT NULL,
	code       VARCHAR(255) NOT NULL UNIQUE, -- OAuth 제공자의 사용자 id
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	PRIMARY KEY (id),
	CONSTRAINT fk_oauth_user FOREIGN KEY (user_id)
		REFERENCES user (id) ON DELETE CASCADE,
	UNIQUE (user_id, provider)               -- 한 사용자가 중복된 provider를 등록할 수 없음
);

CREATE TABLE board
(
	id            BIGINT       NOT NULL AUTO_INCREMENT,
	title         VARCHAR(255) NOT NULL,
	content       TEXT         NOT NULL,
	user_id       BIGINT       NOT NULL,
	view_count    INT          NOT NULL,
	like_count    INT          NOT NULL,
	comment_count INT          NOT NULL,
	is_deleted    BIT(1)       NOT NULL,
	created_at    TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at    TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	PRIMARY KEY (id),
	CONSTRAINT fk_board_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE comment
(
	id         BIGINT       NOT NULL AUTO_INCREMENT,
	content    TEXT         NOT NULL,
	board_id   BIGINT       NOT NULL,
	user_id    BIGINT       NOT NULL,
	is_deleted BIT(1)       NOT NULL DEFAULT b'0',
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NULL     DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_comment_board FOREIGN KEY (board_id) REFERENCES board (id),
	CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE INDEX idx_user_nickname ON user (nickname);
CREATE INDEX idx_oauth_code ON oauth (code);
CREATE INDEX idx_board_user_id ON board (user_id);
CREATE INDEX idx_comment_board_id ON comment (board_id);
CREATE INDEX idx_comment_user_id ON comment (user_id);
