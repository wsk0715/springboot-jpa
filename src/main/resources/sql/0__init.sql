DROP TABLE IF EXISTS oauth_google, oauth_kakao, user;
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
	created_at TIMESTAMP(6) DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) DEFAULT NULL,
	PRIMARY KEY (user_id),
	CONSTRAINT fk_oauth_google_user FOREIGN KEY (user_id)
		REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE oauth_kakao
(
	user_id    BIGINT       NOT NULL,
	code       VARCHAR(255) NOT NULL,
	created_at TIMESTAMP(6) DEFAULT current_timestamp(6),
	updated_at TIMESTAMP(6) DEFAULT NULL,
	PRIMARY KEY (user_id),
	CONSTRAINT fk_oauth_kakao_user FOREIGN KEY (user_id)
		REFERENCES user (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_nickname ON user (nickname);
CREATE INDEX idx_oauth_google_code ON oauth_google (code);
CREATE INDEX idx_oauth_kakao_code ON oauth_kakao (code);

