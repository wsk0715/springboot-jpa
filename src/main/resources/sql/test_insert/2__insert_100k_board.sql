-- 제약 조건 해제
SET autocommit = 0;  -- 오토커밋 해제
SET foreign_key_checks = 0; -- 외래 키 제약조건 해제
ALTER TABLE board DISABLE KEYS;  -- board 테이블 키 제약 조건 해제


-- 재귀 깊이 제한 변경
SET @@cte_max_recursion_depth := 100000;

-- 임시 테이블 생성 (단어 저장)
DROP TEMPORARY TABLE IF EXISTS temp_words;
CREATE TEMPORARY TABLE temp_words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    word VARCHAR(50)
);

-- 단어 삽입
INSERT INTO temp_words (word) VALUES
('사과'), ('바나나'), ('포도'), ('오렌지'), ('파인애플'),
('강아지'), ('고양이'), ('호랑이'), ('사자'), ('아이폰'), ('갤럭시');

-- 임시 테이블 생성 (게시글 데이터 저장)
DROP TEMPORARY TABLE  IF EXISTS temp_board_data;
CREATE TEMPORARY TABLE temp_board_data (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    user_id BIGINT,
    view_count INT,
    like_count INT,
    comment_count INT,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    is_deleted BIT(1)
);

-- 게시글 ID 생성 (1~100000)
INSERT INTO temp_board_data (id)
  WITH RECURSIVE id_seq AS (
    SELECT 1 AS id
    UNION ALL
    SELECT id + 1 FROM id_seq WHERE id < 100000
  )
SELECT id FROM id_seq;

-- 프로시저 생성 - 각 행마다 랜덤 데이터 생성
DELIMITER $$
CREATE PROCEDURE generate_random_data()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE max_id INT;
    DECLARE title_text VARCHAR(255);
    DECLARE content_text TEXT;

    SELECT MAX(id) INTO max_id FROM temp_board_data;

    WHILE i <= max_id DO
        -- 제목 생성 (3개 단어)
        SET title_text = '';
        SET @j = 0;
        WHILE @j < 3 DO
            SELECT word INTO @random_word FROM temp_words ORDER BY RAND() LIMIT 1;
            SET title_text = CONCAT_WS(' ', title_text, @random_word);
            SET @j = @j + 1;
        END WHILE;

        -- 내용 생성 (5개 단어)
        SET content_text = '';
        SET @k = 0;
        WHILE @k < 5 DO
            SELECT word INTO @random_word FROM temp_words ORDER BY RAND() LIMIT 1;
            SET content_text = CONCAT_WS(' ', content_text, @random_word);
            SET @k = @k + 1;
        END WHILE;

        -- 데이터 업데이트
        UPDATE temp_board_data
        SET
            title = TRIM(title_text),
            content = TRIM(content_text),
            user_id = FLOOR(1 + (RAND() * 10000)),
            view_count = FLOOR(RAND() * 100),
            like_count = FLOOR(RAND() * 50),
            comment_count = 0,
            created_at = @time := DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL -FLOOR(RAND() * 334 + 30) DAY),
            updated_at = @time,
            is_deleted = 0
        WHERE id = i;

        SET i = i + 1;
    END WHILE;
END $$
DELIMITER ;

-- 프로시저 실행
CALL generate_random_data();

-- 최종 데이터 삽입
INSERT INTO board (title, content, user_id, view_count, like_count, comment_count, created_at, updated_at, is_deleted)
SELECT title, content, user_id, view_count, like_count, comment_count, created_at, updated_at, is_deleted
FROM temp_board_data;

-- 프로시저 및 임시 테이블 삭제
DROP PROCEDURE generate_random_data;
DROP TEMPORARY TABLE temp_words;
DROP TEMPORARY TABLE temp_board_data;


-- 제약 조건 설정
SET autocommit = 1;  -- 오토커밋 설정
SET foreign_key_checks = 1; -- 외래 키 제약조건 설정
ALTER TABLE board ENABLE KEYS;  -- board 테이블 키 제약 조건 설정
