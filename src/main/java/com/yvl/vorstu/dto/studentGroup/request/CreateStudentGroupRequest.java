package com.yvl.vorstu.dto.studentGroup.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudentGroupRequest {

    @NotBlank
    private String name;
}