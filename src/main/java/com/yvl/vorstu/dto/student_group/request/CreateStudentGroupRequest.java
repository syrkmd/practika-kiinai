package com.yvl.vorstu.dto.student_group.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudentGroupRequest {

    @NotBlank
    private String name;
}