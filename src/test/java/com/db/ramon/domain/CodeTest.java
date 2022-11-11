package com.db.ramon.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeTest {

  @Test
  void objectCreation() {
    final Code country = ImmutableCode.builder().value("asdfghjkjhgfds").build();
    assertEquals("asdfghjkjhgfds", country.value());
  }

  @Test
  void testExpectedException() {
    IllegalStateException thrown =
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> {
              ImmutableCode.builder()
                  .value(
                      "qwertyuiokmnbvcxzaqwertyuiolkjhgfdsaqwertyuioplmnbvcxzsaqwertyuiwertyuioiuytrtyuiojhgbnvcfgyuiuytdfghjkh")
                  .build();
            });
    assertTrue(thrown.getMessage().contains("CODE"));
    assertTrue(thrown.getMessage().contains("must be less than or equals"));
  }
}
