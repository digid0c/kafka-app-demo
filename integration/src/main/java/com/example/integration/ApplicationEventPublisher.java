package com.example.integration;

import com.example.integration.dto.ApplicationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.integration.config.KafkaProperties.MAIN_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventPublisher {

    private final KafkaTemplate<String, ApplicationEventDto> kafkaTemplate;

    public void publish(ApplicationEventDto eventDto) {
        String topic = MAIN_TOPIC;
        log.info("Publishing event {} to topic {}", eventDto, topic);
        kafkaTemplate.send(topic, eventDto);
    }
}
