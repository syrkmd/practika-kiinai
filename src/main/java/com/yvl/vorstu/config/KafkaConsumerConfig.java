package com.yvl.vorstu.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

@Configuration
@ConditionalOnProperty(
        name = "app.delivery.strategy",
        havingValue = "kafka"
)
public class KafkaConsumerConfig {

    @Value("${app.kafka.consumer.max-attempts}")
    private int maxAttempts;

    @Value("${app.kafka.consumer.retry-initial-interval-ms}")
    private long initialInterval;

    @Value("${app.kafka.consumer.retry-multiplier}")
    private double retryMultiplier;

    @Value("${app.kafka.consumer.retry-max-interval-ms}")
    private long retryMaxInterval;

    @Value("${app.kafka.dlt-topic}")
    private String dltTopic;

    @Bean
    public ExponentialBackOffWithMaxRetries backOff() {

        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(maxAttempts);

        backOff.setInitialInterval(initialInterval);
        backOff.setMultiplier(retryMultiplier);
        backOff.setMaxInterval(retryMaxInterval);

        return backOff;
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<String, String> kafkaTemplate) {
        return new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, unused) -> new TopicPartition(dltTopic, record.partition())
        );
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler(
            DeadLetterPublishingRecoverer deadLetterPublishingRecoverer,
            ExponentialBackOffWithMaxRetries backOff
    ) {
        return new DefaultErrorHandler(deadLetterPublishingRecoverer, backOff);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory,
            DefaultErrorHandler defaultErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(defaultErrorHandler);

        return factory;
    }
}
