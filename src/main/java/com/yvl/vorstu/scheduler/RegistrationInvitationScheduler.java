package com.yvl.vorstu.scheduler;

import com.yvl.vorstu.repositories.RegistrationInvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationInvitationScheduler {

    private final RegistrationInvitationRepository repository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredInvitations() {
        repository.deleteByExpiresAtBefore(Instant.now());

        long deleted = repository.deleteByExpiresAtBefore(Instant.now());

        log.info("Deleted {} expired registration invitations", deleted);
    }
}