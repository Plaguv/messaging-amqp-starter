package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.common.EventInstance;
import jakarta.annotation.Nonnull;

public interface EventListener<T extends EventInstance> {

    /**
     *
     * @param event
     */
    void onEvent(@Nonnull T event);

    /**
     *
     * @return
     */
    Class<T> getEventType();
}