package io.github.plaguv.contract.envelope.payload.payment;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;

@Event(domain = EventDomain.PAYMENT)
public record PaymentProcessedEvent(
        float paymentId
) implements EventInstance {

    public PaymentProcessedEvent {
        if (paymentId < 0) {
            throw new IllegalArgumentException("PaymentProcessedEvent attribute 'paymentId' cannot be < 0");
        }
    }
}