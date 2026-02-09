package io.github.plaguv.contract.envelope.routing;

import jakarta.annotation.Nonnull;

public record EventRouting(
   EventScope eventScope,
   String eventWildcard
) {
    public EventRouting {
        if (eventScope == null) {
            throw new IllegalArgumentException("EventRouting attribute 'eventDispatchType' cannot be null");
        }
        if (eventWildcard == null) {
            eventWildcard = "";
        }
    }

    public EventRouting(EventScope eventScope) {
        this(eventScope, "");
    }

    public static EventRouting valueOf(EventScope eventScope) {
        return new EventRouting(eventScope, null);
    }

    public static EventRouting valueOf(EventScope eventScope, String wildcard) {
        return new EventRouting(eventScope, wildcard);
    }

    @Override
    public @Nonnull String toString() {
        return "EventRouting{" +
                "eventDispatchType=" + eventScope +
                ", eventWildcard='" + eventWildcard + '\'' +
                '}';
    }
}