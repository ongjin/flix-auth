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
  'dydwls140',
  '$2a$12$ClzHrKWEbVQUzGwHPO2TMeJ6HjpNlCy5yr2y/S81WaNVCZ7M2y8AW',
  'dydwls140@example.com',
  true,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);

INSERT INTO users (id, username, password, email, enabled, created_at, updated_at)
VALUES (
  2,
  'wh3821',
  '$2a$12$l1J7ITi/OYbqzg7A8988a.hs9fxJ4MBad5blxwkAUM/UXBKGbi3.G',
  'wh3821@example.com',
  true,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);
INSERT INTO users (id, username, password, email, enabled, created_at, updated_at)
VALUES (
  3,
  'wls140',
  '$2a$12$zpk1V.NhKIn3mDuBVmJo0uvo69E1Vw1XGZquplStfRbQYVv8xqbIC',
  'wls140@example.com',
  true,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
);

-- 사용자와 역할(User_Roles) 매핑 삽입
-- user1에게 ROLE_USER 부여 (role_id 1)
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id)
VALUES (3, 2);
