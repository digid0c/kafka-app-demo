package com.example.integration;

import com.example.integration.dto.ApplicationEventDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import static com.example.integration.config.KafkaProperties.KAFKA_ENABLED;
import static com.example.integration.config.KafkaProperties.MAIN_TOPIC;

@Slf4j
public abstract class ApplicationEventListener<T extends ApplicationEventDto> {

    @KafkaListener(topics = MAIN_TOPIC, autoStartup = KAFKA_ENABLED)
    public void onEvent(ConsumerRecord<String, T> record) {
        T applicationEventDto = record.value();
        log.info("Received event {}", applicationEventDto);
        handleEvent(applicationEventDto);
    }

    public abstract void handleEvent(T applicationEventDto);
}
