package io.github.plaguv.starter.properties;

import io.github.plaguv.core.utlity.properties.AmqpProperties;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "amqp")
@Validated
public record AmqpPropertiesConfiguration(
        @NotBlank
        String centralExchange,
        @NotBlank
        String centralApplication
) implements AmqpProperties {}