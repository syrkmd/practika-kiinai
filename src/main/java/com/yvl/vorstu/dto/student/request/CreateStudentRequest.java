package com.yvl.vorstu.dto.student.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStudentRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Long groupId;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
