package com.yvl.vorstu.dto.teacher.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTeacherRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    private String phoneNumber;
}