package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;

/**
 * Strategy for handing off a registration invitation email for delivery.
 * Exactly one implementation is active at a time, selected via
 * app.delivery.strategy (see DirectEmailDispatcher / future Kafka-based
 * implementation). OutboxEventProcessor depends only on this interface and
 * does not know which delivery mechanism is behind it.
 */
public interface RegistrationInvitationEmailDispatcher {

    void dispatch(RegistrationInvitationEmailPayload payload);
}
