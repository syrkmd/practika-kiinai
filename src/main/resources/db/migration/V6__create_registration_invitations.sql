CREATE TABLE registration_invitations
(
    id BIGSERIAL PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),

    email VARCHAR(255) NOT NULL UNIQUE,

    role VARCHAR(20) NOT NULL,

    group_id BIGINT,

    token_hash VARCHAR(64) NOT NULL UNIQUE,

    expires_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_registration_invitations_group
        FOREIGN KEY (group_id)
            REFERENCES student_groups (id)
            ON DELETE SET NULL
);