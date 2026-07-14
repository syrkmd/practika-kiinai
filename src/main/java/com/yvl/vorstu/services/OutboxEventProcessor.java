package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;
import com.yvl.vorstu.entities.OutboxEvent;
import com.yvl.vorstu.entities.OutboxEventStatus;
import com.yvl.vorstu.repositories.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventProcessor {

    private static final long MAX_BACKOFF_SECONDS = 3600;

    private final OutboxRepository repository;
    private final RegistrationInvitationEmailDispatcher registrationInvitationEmailDispatcher;
    private final ObjectMapper objectMapper;

    @Value("${app.outbox.max-attempts:5}")
    private int maxAttempts;

    @Value("${app.outbox.retry-base-delay-seconds:60}")
    private long retryBaseDelaySeconds;

    @Transactional
    public boolean processNext() {
        List<OutboxEvent> claimed = repository.findBatchForProcessing(Instant.now(), 1);

        if (claimed.isEmpty()) {
            return false;
        }

        handle(claimed.getFirst());
        return true;
    }

    private void handle(OutboxEvent event) {
        try {
            deliver(event);
            markSent(event);
        } catch (Exception e) {
            markFailedAttempt(event, e);
        }

        repository.save(event);
    }

    private void deliver(OutboxEvent event) {
        switch (event.getType()) {
            case REGISTRATION_INVITATION_EMAIL -> sendRegistrationInvitationEmail(event);
            default -> throw new IllegalStateException("Unsupported outbox event type: " + event.getType());
        }
    }

    private void sendRegistrationInvitationEmail(OutboxEvent event) {
        RegistrationInvitationEmailPayload payload = objectMapper.readValue(event.getPayload(), RegistrationInvitationEmailPayload.class);

        registrationInvitationEmailDispatcher.dispatch(payload);
    }

    private void markSent(OutboxEvent event) {
        event.setStatus(OutboxEventStatus.SENT);
        event.setProcessedAt(Instant.now());
        event.setLastError(null);
    }

    private void markFailedAttempt(OutboxEvent event, Exception e) {
        int attempts = event.getAttempts() + 1;

        event.setAttempts(attempts);
        event.setLastError(e.getClass().getSimpleName() + ": " + e.getMessage());

        if (attempts >= maxAttempts) {
            event.setStatus(OutboxEventStatus.FAILED);
            event.setProcessedAt(Instant.now());
        } else {
            event.setStatus(OutboxEventStatus.PENDING);
            event.setNextAttemptAt(Instant.now().plusSeconds(backoffSeconds(attempts)));
        }

        log.error("Outbox event {} failed on attempt {}/{}", event.getId(), attempts, maxAttempts, e);
    }

    private long backoffSeconds(int attempts) {
        long delay = retryBaseDelaySeconds * (1L << (attempts - 1));
        return Math.min(delay, MAX_BACKOFF_SECONDS);
    }
}
