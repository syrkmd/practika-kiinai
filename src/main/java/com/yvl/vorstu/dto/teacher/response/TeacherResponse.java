package com.yvl.vorstu.dto.teacher.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;

    private String phoneNumber;
}