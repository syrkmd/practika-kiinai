package com.yvl.vorstu.dto.studentGroup.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentGroupResponse {

    private Long id;

    private String name;
}