package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.TeacherGroupAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherGroupAssignmentRepository extends JpaRepository<TeacherGroupAssignment, Long> {

    boolean existsByTeacherAndStudentGroup(Teacher teacher, StudentGroup studentGroup);
}