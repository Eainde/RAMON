package com.db.ramon.domain;

import com.db.ramon.Wrapped;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class Description extends Domain<String> {
    private static final int DESCRIPTION_MAX_LENGTH = 10000;
    private static final String DESCRIPTION_MAX_LENGTH_VALIDATION_FAILED_MESSAGE = "%s must be less than or equals %s";

    @Value.Check
    public void check() {
        final String value = value();
        Preconditions.checkState(
                value.length() <= DESCRIPTION_MAX_LENGTH,
                DESCRIPTION_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
                "DESCRIPTION",
                DESCRIPTION_MAX_LENGTH,
                value);
    }
}
