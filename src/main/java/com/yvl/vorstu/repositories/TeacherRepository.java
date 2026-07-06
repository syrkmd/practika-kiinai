package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.Teacher;
import com.yvl.vorstu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUser(User user);
}