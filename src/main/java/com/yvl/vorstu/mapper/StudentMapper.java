package com.yvl.vorstu.mapper;

import com.yvl.vorstu.dto.student.request.CreateStudentRequest;
import com.yvl.vorstu.dto.student.request.UpdateStudentRequest;
import com.yvl.vorstu.dto.student.response.StudentResponse;
import com.yvl.vorstu.entities.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {

    public Student toEntity(CreateStudentRequest request) {
        return Student.builder()
                .fio(request.getFio())
                .group(request.getGroup())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .fio(student.getFio())
                .group(student.getGroup())
                .phoneNumber(student.getPhoneNumber())
                .build();
    }

    public void updateEntity(Student student, UpdateStudentRequest request) {
        student.setFio(request.getFio());
        student.setGroup(request.getGroup());
        student.setPhoneNumber(request.getPhoneNumber());
    }

    public List<StudentResponse> toResponseList(List<Student> students) {
        return students.stream()
                .map(this::toResponse)
                .toList();
    }
}
