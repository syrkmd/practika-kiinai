ALTER TABLE students
    ADD COLUMN email VARCHAR(255);

ALTER TABLE teachers
    ADD COLUMN email VARCHAR(255);

UPDATE students s
SET email = u.username || '@sstu.ru'
FROM users u
WHERE s.user_id = u.id;

UPDATE teachers t
SET email = u.username || '@sstu.ru'
FROM users u
WHERE t.user_id = u.id;

ALTER TABLE students
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE teachers
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE students
    ADD CONSTRAINT uk_student_email
        UNIQUE (email);

ALTER TABLE teachers
    ADD CONSTRAINT uk_teacher_email
        UNIQUE (email);

ALTER TABLE students
    ADD CONSTRAINT chk_student_phone
        CHECK (phone_number ~ '^(\+7|8)[0-9]{10}$');

ALTER TABLE teachers
    ADD CONSTRAINT chk_teacher_phone
        CHECK (phone_number ~ '^(\+7|8)[0-9]{10}$');


ALTER TABLE students
    ADD CONSTRAINT chk_student_email
        CHECK (
            email ~ '^[A-Za-z0-9+_.-]+@(mail\.ru|yandex\.ru|sstu\.ru)$'
            );

ALTER TABLE teachers
    ADD CONSTRAINT chk_teacher_email
        CHECK (
            email ~ '^[A-Za-z0-9+_.-]+@(mail\.ru|yandex\.ru|sstu\.ru)$'
            );
