package io.github.plaguv.contract.envelope.routing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventRoutingTest {

    @Test
    @DisplayName("Should throw if null parameter in constructor")
    void throwsOnNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventRouting(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EventRouting(null, null));
    }
}