package com.db.ramon.domain;

import com.db.ramon.Wrapped;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class Parent extends Domain<String> {
    private static final int PARENT_MAX_LENGTH = 100;
    private static final String PARENT_MAX_LENGTH_VALIDATION_FAILED_MESSAGE = "%s must be less than or equals %s";

    @Value.Check
    public void check() {
        final String value = value();
        Preconditions.checkState(
                value.length() <= PARENT_MAX_LENGTH,
                PARENT_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
                "PARENT",
                PARENT_MAX_LENGTH,
                value);
    }
}
