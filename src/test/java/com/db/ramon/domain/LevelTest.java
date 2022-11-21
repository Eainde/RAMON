package com.db.ramon.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevelTest {

    @Test
    void objectCreation() {
        final Level level = ImmutableLevel.builder().value(31).build();
        assertEquals(31, level.value().intValue());
    }

    @Test
    void testExpectedException() {
        IllegalStateException thrown = Assertions.assertThrows(
                IllegalStateException.class,
                () -> ImmutableLevel.builder().value(0).build());
        assertTrue(thrown.getMessage().contains("level cannot be 0"));
    }
}
