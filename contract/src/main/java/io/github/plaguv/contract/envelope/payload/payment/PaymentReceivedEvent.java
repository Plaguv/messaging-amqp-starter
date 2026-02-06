package io.github.plaguv.contract.envelope.payload.payment;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.payload.EventInstance;

@Event(domain = EventDomain.PAYMENT)
public record PaymentReceivedEvent(
        float amount
) implements EventInstance {

    public PaymentReceivedEvent {
        if (amount < 0) {
            throw new IllegalArgumentException("PaymentReceivedEvents attribute 'amount' cannot be < 0");
        }
    }
}