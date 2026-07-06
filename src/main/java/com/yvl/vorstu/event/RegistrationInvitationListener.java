package com.yvl.vorstu.event;

import com.yvl.vorstu.dto.registrationInvitation.InvitationEmailDto;
import com.yvl.vorstu.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationInvitationListener {

    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRegistrationInvitationsCreated(RegistrationInvitationsCreatedEvent event) {
        for (InvitationEmailDto invitation : event.getInvitations()) {
            try {
                emailService.sendInvitation(invitation.getEmail(), invitation.getToken());
            } catch (Exception e) {
                log.error("Failed to send registration invitation email to {}", invitation.getEmail(), e);
            }
        }
    }
}
