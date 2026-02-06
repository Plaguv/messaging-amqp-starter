package io.github.plaguv.messaging.utlity;

import io.github.plaguv.contract.envelope.routing.EventRoutingDescriptor;
import jakarta.annotation.Nonnull;

public interface EventRouter {

    @Nonnull
    String resolveQueue(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);

    @Nonnull
    String resolveExchange(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);

    @Nonnull
    String resolveRoutingKey(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);

    @Nonnull
    String resolveBinding(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);
}