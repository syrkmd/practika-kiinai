package com.yvl.vorstu.scheduler;

import com.yvl.vorstu.services.OutboxEventProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private static final int MAX_EVENTS_PER_TICK = 20;

    private final OutboxEventProcessor processor;

    @Scheduled(fixedDelayString = "${app.outbox.poll-interval-ms:30000}")
    public void processPendingEvents() {
        for (int i = 0; i < MAX_EVENTS_PER_TICK; i++) {
            if (!processor.processNext()) {
                break;
            }
        }
    }
}
