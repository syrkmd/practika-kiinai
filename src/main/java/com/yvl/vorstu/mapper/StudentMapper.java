package com.yvl.vorstu.mapper;

import com.yvl.vorstu.dto.student.request.CreateStudentRequest;
import com.yvl.vorstu.dto.student.request.UpdateStudentRequest;
import com.yvl.vorstu.dto.student.response.StudentResponse;
import com.yvl.vorstu.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(CreateStudentRequest request);

    StudentResponse toResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> students);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Student student, UpdateStudentRequest request);
}
