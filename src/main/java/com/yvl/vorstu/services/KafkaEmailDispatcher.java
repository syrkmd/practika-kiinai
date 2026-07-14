package com.yvl.vorstu.services;


import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;
import com.yvl.vorstu.exception.KafkaPublishException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@ConditionalOnProperty(
        name = "app.delivery.strategy",
        havingValue = "kafka"
)
@RequiredArgsConstructor
public class KafkaEmailDispatcher implements RegistrationInvitationEmailDispatcher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.registration-invitation-topic}")
    private String topic;

    @Value("${app.kafka.send-timeout-seconds}")
    private long sendTimeoutSeconds;

    @Override
    public void dispatch(RegistrationInvitationEmailPayload payload) {

        try {
            String json = objectMapper.writeValueAsString(payload);

            kafkaTemplate.send(topic, payload.getEmail(), json).get(sendTimeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaPublishException(e);
        } catch (ExecutionException | TimeoutException e) {
            throw new KafkaPublishException(e);
        }
    }
}
