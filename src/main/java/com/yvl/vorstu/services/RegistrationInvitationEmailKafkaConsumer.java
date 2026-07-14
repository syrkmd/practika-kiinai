package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;
import com.yvl.vorstu.exception.KafkaConsumerSimulationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Random;

@Service
@ConditionalOnProperty(
        name = "app.delivery.strategy",
        havingValue = "kafka"
)
@RequiredArgsConstructor
public class RegistrationInvitationEmailKafkaConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @Value("${app.kafka.consumer.failure-probability}")
    private double failureProbability;

    @KafkaListener(
            topics = "${app.kafka.registration-invitation-topic}",
            groupId = "${app.kafka.registration-invitation-consumer-group}"
    )
    public void consume(String json) {

        RegistrationInvitationEmailPayload payload = objectMapper.readValue(json, RegistrationInvitationEmailPayload.class);

        if (random.nextDouble() < failureProbability) {
            throw new KafkaConsumerSimulationException();
        }

        emailService.sendInvitation(payload.getEmail(), payload.getToken());
    }
}
