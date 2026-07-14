package com.yvl.vorstu.dto.registrationInvitation.response;

import com.yvl.vorstu.entities.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RegistrationInvitationSummaryResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;

    private String email;
    private Role role;

    private String group;

    private Instant expiresAt;
}
