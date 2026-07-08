package com.yvl.vorstu.dto.registrationInvitation.response;

import com.yvl.vorstu.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationInvitationResponse {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private Role role;
}