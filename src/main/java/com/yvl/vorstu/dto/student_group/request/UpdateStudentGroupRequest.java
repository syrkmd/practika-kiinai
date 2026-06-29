package com.yvl.vorstu.dto.student_group.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStudentGroupRequest {

    @NotBlank
    private String name;
}