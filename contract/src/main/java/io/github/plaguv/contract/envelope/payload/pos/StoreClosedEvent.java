package io.github.plaguv.contract.envelope.payload.pos;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

@Event(domain = EventDomain.STORE)
public record StoreClosedEvent(
        long storeId
) {

    public StoreClosedEvent {
        if (storeId < 1) {
            throw new IllegalArgumentException("StoreClosedEvents attribute 'storeId' cannot be < 1");
        }
    }
}