-- Create user_auth table
CREATE TABLE user_auth (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create user_profile table
CREATE TABLE user_profile (
    id BIGINT PRIMARY KEY,
    nickname VARCHAR(50),
    bio TEXT,
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- Move existing data to new tables
INSERT INTO user_auth (user_id, password, refresh_token, enabled)
SELECT id, password, refresh_token, enabled FROM users;

INSERT INTO user_profile (id, nickname)
SELECT id, username FROM users;

-- Drop columns from users table
ALTER TABLE users DROP COLUMN password;
ALTER TABLE users DROP COLUMN refresh_token;
ALTER TABLE users DROP COLUMN enabled; 