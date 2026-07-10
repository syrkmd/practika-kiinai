package com.yvl.vorstu.dto.registrationInvitation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInvitationEmailPayload {

    private String email;
    private String token;
}
