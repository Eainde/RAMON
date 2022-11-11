package com.db.ramon.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderIdTest {

  @Test
  void objectCreation() {
    final OrderId userId = ImmutableOrderId.builder().value(31L).build();
    assertEquals(31, userId.value().intValue());
  }

  @Test
  void testExpectedException() {
    IllegalStateException thrown =
        Assertions.assertThrows(
            IllegalStateException.class, () -> ImmutableOrderId.builder().value(0L).build());
    assertTrue(thrown.getMessage().contains("order id cannot be 0"));
  }
}
