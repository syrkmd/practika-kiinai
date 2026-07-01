package com.yvl.vorstu.dto.studentGroup.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStudentGroupRequest {

    @NotBlank
    private String name;
}