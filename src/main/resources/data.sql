-- 역할(ROLE) 데이터 삽입
INSERT INTO roles (id, name, description)
VALUES (1, 'ROLE_USER', 'Standard user role');

INSERT INTO roles (id, name, description)
VALUES (2, 'ROLE_ADMIN', 'Administrator role');

-- 사용자(User) 데이터 삽입
-- 아래 비밀번호는 "password"의 BCrypt 해시 예시입니다.
INSERT INTO users (id, username, password, email, enabled, created_at, updated_at)
VALUES (
  1,
  'user1',
  '$2a$10$w3HHjxdx8ljO5TeOObdD3OGbWbbA2OP7uH.7KKPQQ.lYwfr/viDUW',
  'user1@example.com',
  true,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);

-- 사용자와 역할(User_Roles) 매핑 삽입
-- user1에게 ROLE_USER 부여 (role_id 1)
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
