package io.github.plaguv.contract.envelope.metadata;

import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,
        Instant occurredAt,
        Optional<Class<?>> producer
) {
    public EventMetadata {
        if (eventId == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'eventId' cannot be null");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("EventMetadata attribute 'occurredAt' cannot be null");
        }
    }

    public EventMetadata(UUID eventId, Instant occurredAt, Class<?> producer) {
        this(
                eventId,
                occurredAt,
                Optional.of(producer)
        );
    }

    public EventMetadata(UUID eventId, Instant occurredAt, Object producer) {
        this(
                eventId,
                occurredAt,
                producer == null ? Optional.empty() : Optional.of(producer.getClass())
        );
    }

    public EventMetadata(Class<?> producer) {
        this(
                UUID.randomUUID(),
                Instant.now(),
                Optional.of(producer)
        );
    }

    public static EventMetadata now() {
        return new EventMetadata(
                UUID.randomUUID(),
                Instant.now(),
                Optional.empty()
        );
    }

    @Override
    public @Nonnull String toString() {
        return "EventMetadata{" +
                "eventId=" + eventId +
                ", occurredAt=" + occurredAt +
                ", producer=" + producer +
                '}';
    }
}