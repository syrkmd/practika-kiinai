package com.yvl.vorstu.exception;

public class KafkaConsumerSimulationException extends RuntimeException {

    public KafkaConsumerSimulationException() {
        super("Simulated Kafka consumer processing failure");
    }
}
