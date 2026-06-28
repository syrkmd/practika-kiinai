package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    Optional<StudentGroup> findByName(String name);
}