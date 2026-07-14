package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "app.delivery.strategy",
        havingValue = "worker",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class DirectEmailDispatcher implements RegistrationInvitationEmailDispatcher {

    private final EmailService emailService;

    @Override
    public void dispatch(RegistrationInvitationEmailPayload payload) {
        emailService.sendInvitation(payload.getEmail(), payload.getToken());
    }
}
