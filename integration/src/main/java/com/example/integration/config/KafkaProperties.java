package com.example.integration.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:kafka.properties")
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    public static final String MAIN_TOPIC = "main";
    public static final String KAFKA_ENABLED = "true";

    private String bootstrapServers;
    private String groupId;
}
