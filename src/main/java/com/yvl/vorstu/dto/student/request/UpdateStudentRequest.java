package com.yvl.vorstu.dto.student.request;

import com.yvl.vorstu.validation.annotation.ValidEmail;
import com.yvl.vorstu.validation.annotation.ValidPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStudentRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    @ValidPhone
    private String phoneNumber;

    @NotBlank
    @ValidEmail
    private String email;

    @NotNull
    private Long groupId;
}