package io.github.plaguv.contract.envelope.payload;

public record StoreClosedEvent(
        Long storeId
) implements EventInstance {
    public StoreClosedEvent {
        if (storeId == null) {
            throw new IllegalArgumentException("StoreClosedEvents storeId cannot be null");
        }
    }
}