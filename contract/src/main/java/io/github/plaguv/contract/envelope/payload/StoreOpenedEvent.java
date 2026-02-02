package io.github.plaguv.contract.envelope.payload;

public record StoreOpenedEvent(
        Long storeId
) implements EventInstance {
    public StoreOpenedEvent {
        if (storeId == null) {
            throw new IllegalArgumentException("StoreOpenedEvents storeId cannot be null");
        }
    }
}