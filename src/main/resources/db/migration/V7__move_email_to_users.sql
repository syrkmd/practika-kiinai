ALTER TABLE users
    ADD COLUMN email VARCHAR(255);

UPDATE users u
SET email = s.email
FROM students s
WHERE s.user_id = u.id;

UPDATE users u
SET email = t.email
FROM teachers t
WHERE t.user_id = u.id;

-- ADMIN has no students/teachers row, so it is not covered by the backfill above.
UPDATE users
SET email = 'admin@sstu.ru'
WHERE username = 'admin'
  AND email IS NULL;

-- Safety net: any other user without a matching students/teachers row
-- (e.g. a profile deleted after the account was created) would otherwise
-- keep email = NULL and break the NOT NULL constraint below.
UPDATE users
SET email = username || '@sstu.ru'
WHERE email IS NULL;

ALTER TABLE users
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT uk_users_email
        UNIQUE (email);
