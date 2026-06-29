package com.yvl.vorstu.mapper;

import com.yvl.vorstu.dto.teacher_group_assignment.request.CreateTeacherGroupAssignmentRequest;
import com.yvl.vorstu.dto.teacher_group_assignment.response.TeacherGroupAssignmentResponse;
import com.yvl.vorstu.entities.TeacherGroupAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherGroupAssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "studentGroup", ignore = true)
    TeacherGroupAssignment toEntity(CreateTeacherGroupAssignmentRequest request);

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherLastName", source = "teacher.lastName")
    @Mapping(target = "groupId", source = "studentGroup.id")
    @Mapping(target = "groupName", source = "studentGroup.name")
    TeacherGroupAssignmentResponse toResponse(TeacherGroupAssignment assignment);
}