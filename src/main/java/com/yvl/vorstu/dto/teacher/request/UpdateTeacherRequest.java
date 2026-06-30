package com.yvl.vorstu.dto.teacher.request;

import com.yvl.vorstu.validation.annotation.ValidEmail;
import com.yvl.vorstu.validation.annotation.ValidPhone;
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
    @ValidPhone
    private String phoneNumber;

    @NotBlank
    @ValidEmail
    private String email;
}