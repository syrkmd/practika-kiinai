package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.TeacherGroupAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherGroupAssignmentRepository extends JpaRepository<TeacherGroupAssignment, Long> {

    boolean existsByTeacherAndStudentGroup(Teacher teacher, StudentGroup studentGroup);

    List<TeacherGroupAssignment> findAllByTeacher(Teacher teacher);

    @Query("""
                select assignment.studentGroup
                from TeacherGroupAssignment assignment
                where assignment.teacher = :teacher
            """)
    List<StudentGroup> findGroupsByTeacher(@Param("teacher") Teacher teacher);
}