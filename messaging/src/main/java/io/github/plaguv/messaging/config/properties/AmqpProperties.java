package io.github.plaguv.messaging.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amqp")
public record AmqpProperties(
    String centralApplication
) {
    public AmqpProperties {
        if (centralApplication == null || centralApplication.isBlank()) {
            throw new IllegalArgumentException("Property 'amqp.application-name' cannot be null or blank");
        }
    }
}