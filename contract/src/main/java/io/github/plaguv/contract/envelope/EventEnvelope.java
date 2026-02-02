package io.github.plaguv.contract.envelope;

import io.github.plaguv.contract.envelope.payload.EventInstance;
import io.github.plaguv.contract.envelope.metadata.EventMetadata;
import io.github.plaguv.contract.envelope.metadata.EventVersion;
import io.github.plaguv.contract.envelope.routing.EventDispatchType;
import io.github.plaguv.contract.envelope.routing.EventRouting;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope(
        EventMetadata metadata,
        EventRouting eventRouting,
        EventInstance payload
) {
    public EventEnvelope {
        if (metadata == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'metadata' cannot be null");
        }
        if (eventRouting == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'routing' cannot be null");
        }
        if (payload == null) {
            throw new IllegalArgumentException("EventEnvelope attribute 'payload' cannot be null");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID eventId;
        private EventVersion eventVersion;
        private Instant occurredAt;
        private Class<?> producer;

        private EventDispatchType eventDispatchType;

        private EventInstance payload;

        private Builder() {}

        public Builder ofMetadata(EventMetadata metadata) {
            if (metadata == null) {
                throw new IllegalArgumentException("EventMetadata attribute 'metadata' cannot be null");
            }
            this.eventId = metadata.eventId();
            this.eventVersion = metadata.eventVersion();
            this.occurredAt = metadata.occurredAt();
            this.producer = metadata.producer();
            return this;
        }

        public Builder withEventId(UUID eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withEventVersion(EventVersion eventVersion) {
            this.eventVersion = eventVersion;
            return this;
        }

        public Builder withOccurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public Builder withProducer(Class<?> producer) {
            this.producer = producer;
            return this;
        }

        public Builder ofRouting(EventRouting eventRouting) {
            if (eventRouting == null) {
                throw new IllegalArgumentException("EventRouting attribute 'eventRouting' cannot be null");
            }
            this.eventDispatchType = eventRouting.eventDispatchType();
            return this;
        }

        public Builder withEventDispatchType(EventDispatchType eventDispatchType) {
            this.eventDispatchType = eventDispatchType;
            return this;
        }

        public Builder ofPayload(EventInstance payload) {
            this.payload = payload;
            return this;
        }

        public EventEnvelope build() {
            EventMetadata metadata;

            if (eventId == null && occurredAt == null) {
                metadata = new EventMetadata(
                        eventVersion,
                        producer
                );
            } else {
                metadata = new EventMetadata(
                  eventId,
                  eventVersion,
                  occurredAt,
                  producer
                );
            }

            EventRouting routing = new EventRouting(eventDispatchType);

            return new EventEnvelope(
                    metadata,
                    routing,
                    payload
            );
        }
    }
}