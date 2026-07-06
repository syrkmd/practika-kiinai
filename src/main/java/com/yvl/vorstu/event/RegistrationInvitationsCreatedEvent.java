package com.yvl.vorstu.event;

import com.yvl.vorstu.dto.registrationInvitation.InvitationEmailDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationInvitationsCreatedEvent {

    private List<InvitationEmailDto> invitations;
}
