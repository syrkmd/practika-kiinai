package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.Student;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByStudentGroup(StudentGroup studentGroup, Pageable pageable);

    Page<Student> findByStudentGroupIn(
            Collection<StudentGroup> studentGroups,
            Pageable pageable
    );

    Optional<Student> findByUser(User user);
}
