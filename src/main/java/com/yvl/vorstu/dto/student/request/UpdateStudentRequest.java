package com.yvl.vorstu.dto.student.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStudentRequest {

    @NotBlank(message = "FIO must not be blank")
    private String fio;

    @NotBlank(message = "Group must not be blank")
    private String group;

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;
}