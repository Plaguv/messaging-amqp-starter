package io.github.plaguv.messaging.utlity;

import io.github.plaguv.contract.envelope.routing.EventRoutingDescriptor;
import jakarta.annotation.Nonnull;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

public interface TopologyDeclarer {

    Exchange declareExchangeIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);

    Queue declareQueueIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);

    Binding declareBindingIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor);
}