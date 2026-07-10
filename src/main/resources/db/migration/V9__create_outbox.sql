CREATE TABLE outbox
(
    id           UUID PRIMARY KEY,

    type         VARCHAR(50) NOT NULL,

    payload      JSONB NOT NULL,

    status       VARCHAR(20) NOT NULL,

    attempts     INT NOT NULL DEFAULT 0,

    created_at   TIMESTAMP NOT NULL,

    processed_at TIMESTAMP,

    last_error   TEXT
);
