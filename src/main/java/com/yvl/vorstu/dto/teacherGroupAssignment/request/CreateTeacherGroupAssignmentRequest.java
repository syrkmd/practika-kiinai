package com.yvl.vorstu.dto.teacherGroupAssignment.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTeacherGroupAssignmentRequest {

    @NotNull
    private Long teacherId;

    @NotNull
    private Long groupId;
}