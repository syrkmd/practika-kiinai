package com.yvl.vorstu.exception;

public class KafkaPublishException extends RuntimeException {

    public KafkaPublishException(Throwable cause) {
        super("Failed to publish message to Kafka", cause);
    }
}