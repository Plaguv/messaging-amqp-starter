package io.github.plaguv.messaging.publisher;

import io.github.plaguv.contracts.common.EventEnvelope;
import jakarta.annotation.Nonnull;

public interface TopologyDeclarer {

    /**
     *
     * @param eventEnvelope e
     */
    void declareExchangeIfAbsent(@Nonnull EventEnvelope eventEnvelope);

    /**
     *
     * @param eventEnvelope e
     */
    void declareQueueIfAbsent(@Nonnull EventEnvelope eventEnvelope);

    /**
     *
     * @param eventEnvelope e
     */
    void declareBindingIfAbsent(@Nonnull EventEnvelope eventEnvelope);
}