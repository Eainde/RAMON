package com.db.ramon.domain;

import com.db.ramon.Wrapped;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class Level extends Domain<Integer> {
    private static final String LEVEL_VALIDATION_FAILED_MESSAGE = "order id cannot be 0";

    @Value.Check
    public void check() {
        final int value = value();
        Preconditions.checkState(value != 0, LEVEL_VALIDATION_FAILED_MESSAGE);
    }
}
