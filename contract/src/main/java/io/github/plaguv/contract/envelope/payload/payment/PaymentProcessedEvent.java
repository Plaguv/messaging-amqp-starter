package io.github.plaguv.contract.envelope.payload.payment;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

@Event(domain = EventDomain.PAYMENT)
public record PaymentProcessedEvent(
        float paymentId
) {

    public PaymentProcessedEvent {
        if (paymentId < 1) {
            throw new IllegalArgumentException("PaymentProcessedEvent attribute 'paymentId' cannot be < 1");
        }
    }
}