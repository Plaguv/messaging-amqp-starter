package io.github.plaguv.contract.envelope.payload.payment;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;

@Event(domain = EventDomain.PAYMENT)
public record PaymentReceivedEvent(
        float amount
) {

    public PaymentReceivedEvent {
        if (amount < 1) {
            throw new IllegalArgumentException("PaymentReceivedEvents attribute 'amount' cannot be < 1");
        }
    }
}