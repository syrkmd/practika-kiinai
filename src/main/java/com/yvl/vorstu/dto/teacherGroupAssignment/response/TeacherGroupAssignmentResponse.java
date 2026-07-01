package com.yvl.vorstu.dto.teacherGroupAssignment.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherGroupAssignmentResponse {

    private Long id;

    private Long teacherId;
    private String teacherFirstName;
    private String teacherLastName;

    private Long groupId;
    private String groupName;
}