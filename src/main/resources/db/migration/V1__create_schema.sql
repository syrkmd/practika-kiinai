CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

CREATE TABLE student_groups
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE teachers
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    middle_name  VARCHAR(100),
    phone_number VARCHAR(20),

    user_id      BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_teacher_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE students
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    middle_name  VARCHAR(100),
    phone_number VARCHAR(20),

    group_id     BIGINT NOT NULL,
    user_id      BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_student_group
        FOREIGN KEY (group_id)
            REFERENCES student_groups (id),

    CONSTRAINT fk_student_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE teacher_group_assignments
(
    id         BIGSERIAL PRIMARY KEY,

    teacher_id BIGINT NOT NULL,
    group_id   BIGINT NOT NULL,

    CONSTRAINT fk_teacher_assignment_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_teacher_assignment_group
        FOREIGN KEY (group_id)
            REFERENCES student_groups (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_teacher_assignment
        UNIQUE (teacher_id, group_id)
);