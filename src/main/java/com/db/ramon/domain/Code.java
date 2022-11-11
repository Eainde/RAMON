package com.db.ramon.domain;

import com.db.ramon.Wrapped;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class Code extends Domain<String> {
  private static final int CODE_MAX_LENGTH = 100;
  private static final String CODE_MAX_LENGTH_VALIDATION_FAILED_MESSAGE =
      "%s must be less than or equals %s";

  @Value.Check
  public void check() {
    final String value = value();
    Preconditions.checkState(
        value.length() <= CODE_MAX_LENGTH,
        CODE_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
        "CODE",
        CODE_MAX_LENGTH,
        value);
  }
}
