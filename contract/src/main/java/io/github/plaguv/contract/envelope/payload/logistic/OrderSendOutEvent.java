package io.github.plaguv.contract.envelope.payload.logistic;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;

@Event(domain = EventDomain.LOGISTIC)
public record OrderSendOutEvent(long storeId) implements EventInstance {

    public OrderSendOutEvent {
        if (storeId < 0) {
            throw new IllegalArgumentException("OrderSendOutEvent attribute 'storeId' cannot be < 0");
        }
    }
}