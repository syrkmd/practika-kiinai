package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> getByGroup(String group);
}
