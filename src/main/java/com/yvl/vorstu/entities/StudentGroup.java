package com.yvl.vorstu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student_groups")
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "studentGroup")
    private List<TeacherGroupAssignment> teacherGroupsAssignments = new ArrayList<>();
}