package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;


public interface RegistrationInvitationEmailDispatcher {

    void dispatch(RegistrationInvitationEmailPayload payload);
}
