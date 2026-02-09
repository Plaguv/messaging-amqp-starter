package io.github.plaguv.contract.envelope.routing;

import io.github.plaguv.contract.envelope.EventEnvelope;
import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

import java.util.Optional;

public record EventRoutingDescriptor(
        Class<?> type,
        EventDomain domain,
        EventScope dispatchType,
        Optional<String> wildcard
) {
    public EventRoutingDescriptor {
        if (type == null) {
            throw new IllegalArgumentException("EventRoutingDescriptor attribute 'type' cannot be null");
        }
        if (!type.isAnnotationPresent(Event.class)) {
            throw new IllegalStateException("EventRoutingDescriptor attribute 'type' must be annotated with @Event in its implementing class");
        }
        if (domain == null) {
            throw new IllegalArgumentException("EventRoutingDescriptor attribute 'domain' cannot be null");
        }
        if (dispatchType == null) {
            throw new IllegalArgumentException("EventRoutingDescriptor attribute 'dispatchType' cannot be null");
        }
    }

    public EventRoutingDescriptor(Class<?> type, EventDomain domain, EventScope dispatchType) {
        this(type, domain, dispatchType, Optional.empty());
    }

    public static EventRoutingDescriptor of(EventEnvelope eventEnvelope) {
        if (eventEnvelope == null) {
            throw new IllegalArgumentException("Parameter attribute 'eventEnvelope' cannot be null");
        }

        return new EventRoutingDescriptor(
                eventEnvelope.payload().getClass(),
                eventEnvelope.payload().getClass().getAnnotation(Event.class).domain(),
                eventEnvelope.routing().eventScope()
        );
    }
}