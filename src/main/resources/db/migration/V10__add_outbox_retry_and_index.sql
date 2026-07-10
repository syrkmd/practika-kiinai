ALTER TABLE outbox
    ADD COLUMN next_attempt_at TIMESTAMP;

CREATE INDEX idx_outbox_pending_created_at
    ON outbox (created_at)
    WHERE status = 'PENDING';
