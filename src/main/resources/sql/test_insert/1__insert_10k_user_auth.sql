-- 제약 조건 해제
SET autocommit = 0;  -- 오토커밋 해제
SET foreign_key_checks = 0; -- 외래 키 제약조건 해제
ALTER TABLE user DISABLE KEYS;  -- user 테이블 키 제약 조건 해제
ALTER TABLE auth DISABLE KEYS;  -- auth 테이블 키 제약 조건 해제


-- 재귀 깊이 제한 변경
SET @@cte_max_recursion_depth := 10000;

-- CTE를 이용해 user 테이블 삽입
INSERT INTO user
  (nickname, created_at, updated_at, is_deleted)
WITH RECURSIVE user_seq AS (SELECT 1 AS id
                            UNION ALL
                            SELECT id + 1
                            FROM user_seq
                            WHERE id < 10000)
SELECT
  concat('사용자', user_seq.id),
  @time := DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL -FLOOR(RAND() * 30 + 365) DAY) AS created_at,
  @time,
  IF(RAND() < 0.05, 1, 0) AS is_deleted -- 약 5%의 사용자가 삭제된 상태
FROM user_seq;

-- CTE를 이용해 auth 테이블 삽입
INSERT INTO auth
  (login_id, password, user_id, created_at, updated_at)
WITH RECURSIVE auth_seq AS (SELECT 1 AS id
                            UNION ALL
                            SELECT id + 1
                            FROM auth_seq
                            WHERE id < 10000)
SELECT
  concat('user', auth_seq.id),
  '$2a$10$9fN9YDiQEpQAAh8JjgfjCuHsl5e6YUGXuxw8gyujnOxqRzT96Ey7a',  -- password1@ - Bcrypt(10)
  auth_seq.id,
  @time,
  @time
FROM auth_seq;


-- 제약 조건 설정
SET autocommit = 1;  -- 오토커밋 설정
SET foreign_key_checks = 1; -- 외래 키 제약조건 설정
ALTER TABLE user ENABLE KEYS;  -- user 테이블 키 제약 조건 설정
ALTER TABLE auth ENABLE KEYS;  -- auth 테이블 키 제약 조건 설정
