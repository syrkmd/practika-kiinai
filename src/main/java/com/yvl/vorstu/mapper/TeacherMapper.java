package com.yvl.vorstu.mapper;

import com.yvl.vorstu.dto.teacher.request.CreateTeacherRequest;
import com.yvl.vorstu.dto.teacher.request.UpdateTeacherRequest;
import com.yvl.vorstu.dto.teacher.response.TeacherResponse;
import com.yvl.vorstu.entities.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "teacherGroupAssignments", ignore = true)
    Teacher toEntity(CreateTeacherRequest request);

    @Mapping(target = "email", source = "user.email")
    TeacherResponse toResponse(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "teacherGroupAssignments", ignore = true)
    void updateEntity(
            @MappingTarget Teacher teacher,
            UpdateTeacherRequest request
    );
}