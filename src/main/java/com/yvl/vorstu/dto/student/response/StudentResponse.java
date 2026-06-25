package com.yvl.vorstu.dto.student.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;
}
