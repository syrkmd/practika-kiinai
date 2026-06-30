package com.yvl.vorstu.dto.student.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;

    private String phoneNumber;
    private String email;

    private String group;
}
