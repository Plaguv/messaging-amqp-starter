package io.github.plaguv.contract.envelope.payload.logistic;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

@Event(domain = EventDomain.LOGISTIC)
public record OrderSendOutEvent(
        long storeId
) {

    public OrderSendOutEvent {
        if (storeId < 1) {
            throw new IllegalArgumentException("OrderSendOutEvent attribute 'storeId' cannot be < 1");
        }
    }
}