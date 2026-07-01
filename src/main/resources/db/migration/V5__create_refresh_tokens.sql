CREATE TABLE refresh_tokens
(
    id BIGSERIAL PRIMARY KEY,

    token_hash VARCHAR(64) NOT NULL UNIQUE,

    jti VARCHAR(36) NOT NULL UNIQUE,

    expires_at TIMESTAMP NOT NULL,

    created_at TIMESTAMP NOT NULL,

    revoked BOOLEAN NOT NULL,

    user_id BIGINT NOT NULL,

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);