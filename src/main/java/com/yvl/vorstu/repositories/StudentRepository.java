package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByStudentGroupName(String name, Pageable pageable);
}
