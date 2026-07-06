ALTER TABLE students
    DROP CONSTRAINT IF EXISTS uk_student_email;

ALTER TABLE students
    DROP CONSTRAINT IF EXISTS chk_student_email;

ALTER TABLE teachers
    DROP CONSTRAINT IF EXISTS uk_teacher_email;

ALTER TABLE teachers
    DROP CONSTRAINT IF EXISTS chk_teacher_email;

ALTER TABLE students
    DROP COLUMN email;

ALTER TABLE teachers
    DROP COLUMN email;
