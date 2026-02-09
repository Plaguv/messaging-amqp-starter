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
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventRouting.valueOf(null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> EventRouting.valueOf(null, null));
    }

    @Test
    @DisplayName("Constructor should keep field values")
    void constructorKeepsParameters() {
        String wildcard = "topic";
        EventScope eventScope = EventScope.BROADCAST;
        EventRouting eventRouting;

        eventRouting = new EventRouting(eventScope, wildcard);
        Assertions.assertEquals(eventScope, eventRouting.eventScope());
        Assertions.assertEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = new EventRouting(eventScope);
        Assertions.assertEquals(eventScope, eventRouting.eventScope());
        Assertions.assertNotEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = EventRouting.valueOf(eventScope, wildcard);
        Assertions.assertEquals(eventScope, eventRouting.eventScope());
        Assertions.assertEquals(wildcard, eventRouting.eventWildcard());

        eventRouting = EventRouting.valueOf(eventScope);
        Assertions.assertEquals(eventScope, eventRouting.eventScope());
        Assertions.assertNotEquals(wildcard, eventRouting.eventWildcard());
    }
}