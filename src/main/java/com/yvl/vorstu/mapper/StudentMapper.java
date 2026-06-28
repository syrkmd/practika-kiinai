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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studentGroup", ignore = true)
    @Mapping(target = "user", ignore = true)
    Student toEntity(CreateStudentRequest request);

    @Mapping(target = "group", source = "studentGroup.name")
    StudentResponse toResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> students);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studentGroup", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget Student student, UpdateStudentRequest request);
}
