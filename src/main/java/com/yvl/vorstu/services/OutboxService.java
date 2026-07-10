package com.yvl.vorstu.services;

import com.yvl.vorstu.entities.OutboxEvent;
import com.yvl.vorstu.entities.OutboxEventType;
import com.yvl.vorstu.repositories.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository repository;
    private final ObjectMapper objectMapper;

    /**
     * Payload should be a plain, already-loaded DTO — do not pass JPA
     * entities directly, to avoid lazy-initialization issues on serialization.
     */
    public UUID publish(OutboxEventType type, Object payload) {
        OutboxEvent event = OutboxEvent.builder()
                .type(type)
                .payload(objectMapper.writeValueAsString(payload))
                .build();

        return repository.save(event).getId();
    }
}
