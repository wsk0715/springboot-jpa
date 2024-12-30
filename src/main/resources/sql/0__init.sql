DROP TABLE IF EXISTS board, oauth_google, oauth_kakao, user;
CREATE TABLE user
(
	id         BIGINT       NOT NULL AUTO_INCREMENT,
	nickname   VARCHAR(20)  NOT NULL,
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	is_deleted BIT(1)       NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE oauth_google
(
	user_id    BIGINT       NOT NULL,
	code       VARCHAR(255) NOT NULL,
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	PRIMARY KEY (user_id),
	CONSTRAINT fk_oauth_google_user FOREIGN KEY (user_id)
		REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE oauth_kakao
(
	user_id    BIGINT       NOT NULL,
	code       VARCHAR(255) NOT NULL,
	created_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) NOT NULL DEFAULT current_timestamp(6),
	PRIMARY KEY (user_id),
	CONSTRAINT fk_oauth_kakao_user FOREIGN KEY (user_id)
		REFERENCES user (id) ON DELETE CASCADE
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


CREATE INDEX idx_user_nickname ON user (nickname);
CREATE INDEX idx_oauth_google_code ON oauth_google (code);
CREATE INDEX idx_oauth_kakao_code ON oauth_kakao (code);
CREATE INDEX idx_board_user_id ON board (user_id);
