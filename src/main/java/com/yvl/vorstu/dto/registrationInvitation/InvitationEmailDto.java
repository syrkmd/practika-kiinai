package com.yvl.vorstu.dto.registrationInvitation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvitationEmailDto {

    private String email;
    private String token;
}