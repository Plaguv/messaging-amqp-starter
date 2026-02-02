package io.github.plaguv.contract.envelope.routing;

public record EventRouting(
   EventDispatchType eventDispatchType,
   String wildcard
) {
    public EventRouting {
        if (eventDispatchType == null) {
            throw new IllegalArgumentException("EventRouting attribute 'eventDispatchType' cannot be null");
        }
        if (wildcard == null) {
            wildcard = "";
        }
    }

    public EventRouting(EventDispatchType eventDispatchType) {
        this(eventDispatchType, "");
    }
}