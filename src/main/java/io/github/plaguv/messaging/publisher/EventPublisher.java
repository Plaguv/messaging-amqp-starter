package io.github.plaguv.messaging.publisher;

import io.github.plaguv.contracts.common.EventEnvelope;
import jakarta.annotation.Nonnull;

public interface EventPublisher {

    /**
     *
     * @param eventEnvelope
     */
    void publishMessage(@Nonnull EventEnvelope eventEnvelope);
}