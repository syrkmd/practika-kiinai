package com.yvl.vorstu.mapper;


import com.yvl.vorstu.dto.student_group.request.CreateStudentGroupRequest;
import com.yvl.vorstu.dto.student_group.request.UpdateStudentGroupRequest;
import com.yvl.vorstu.dto.student_group.response.StudentGroupResponse;
import com.yvl.vorstu.entities.StudentGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentGroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacherGroupsAssignments", ignore = true)
    StudentGroup toEntity(CreateStudentGroupRequest request);

    StudentGroupResponse toResponse(StudentGroup group);

    List<StudentGroupResponse> toResponseList(List<StudentGroup> groups);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacherGroupsAssignments", ignore = true)
    void updateEntity(@MappingTarget StudentGroup group, UpdateStudentGroupRequest request);
}