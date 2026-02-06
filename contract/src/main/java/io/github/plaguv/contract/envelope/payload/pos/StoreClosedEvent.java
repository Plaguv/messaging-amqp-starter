package io.github.plaguv.contract.envelope.payload.pos;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;

@Event(domain = EventDomain.STORE)
public record StoreClosedEvent(
        long storeId
) implements EventInstance {

    public StoreClosedEvent {
        if (storeId < 0) {
            throw new IllegalArgumentException("StoreClosedEvents attribute 'storeId' cannot be < 0");
        }
    }
}