INSERT INTO student_groups (name)
VALUES ('ИС-21'),
       ('ИС-22'),
       ('ПИ-21');

INSERT INTO users (username, password, role)
VALUES ('student1', '$2a$10$REPLACE_WITH_BCRYPT_HASH', 'STUDENT'),
       ('teacher1', '$2a$10$REPLACE_WITH_BCRYPT_HASH', 'TEACHER');

INSERT INTO students (
    first_name,
    last_name,
    middle_name,
    phone_number,
    group_id,
    user_id
)
VALUES (
           'Иван',
           'Иванов',
           'Иванович',
           '+79990000001',
           (SELECT id FROM student_groups WHERE name = 'ИС-21'),
           (SELECT id FROM users WHERE username = 'student1')
       );

INSERT INTO teachers (
    first_name,
    last_name,
    middle_name,
    phone_number,
    user_id
)
VALUES (
           'Петр',
           'Петров',
           'Петрович',
           '+79990000002',
           (SELECT id FROM users WHERE username = 'teacher1')
       );

INSERT INTO teacher_group_assignments (
    teacher_id,
    group_id
)
VALUES (
           (SELECT id FROM teachers
            WHERE user_id = (SELECT id FROM users WHERE username = 'teacher1')),
           (SELECT id FROM student_groups WHERE name = 'ИС-21')
       );

INSERT INTO teacher_group_assignments (
    teacher_id,
    group_id
)
VALUES (
           (SELECT id FROM teachers
            WHERE user_id = (SELECT id FROM users WHERE username = 'teacher1')),
           (SELECT id FROM student_groups WHERE name = 'ИС-22')
       );