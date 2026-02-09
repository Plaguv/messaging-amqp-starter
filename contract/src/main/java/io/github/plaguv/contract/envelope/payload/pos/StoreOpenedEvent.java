package io.github.plaguv.contract.envelope.payload.pos;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

@Event(domain = EventDomain.STORE)
public record StoreOpenedEvent(
        long storeId
) {

    public StoreOpenedEvent {
        if (storeId < 1) {
            throw new IllegalArgumentException("StoreOpenedEvents attribute 'storeId' cannot be < 1");
        }
    }
}