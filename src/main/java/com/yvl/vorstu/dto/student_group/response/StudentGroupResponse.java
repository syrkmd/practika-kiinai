package com.yvl.vorstu.dto.student_group.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentGroupResponse {

    private Long id;

    private String name;
}