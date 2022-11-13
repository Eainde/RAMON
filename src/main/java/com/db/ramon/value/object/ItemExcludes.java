package com.db.ramon.value.object;

import com.db.ramon.Wrapped;
import com.db.ramon.domain.Domain;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class ItemExcludes extends Domain<String> {
    private static final int ITEM_EXCLUDES_MAX_LENGTH = 100000;
    private static final String ITEM_EXCLUDES_MAX_LENGTH_VALIDATION_FAILED_MESSAGE =
            "%s must be less than or equals %s";

    @Value.Check
    public void check() {
        final String value = value();
        Preconditions.checkState(
                value.length() <= ITEM_EXCLUDES_MAX_LENGTH,
                ITEM_EXCLUDES_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
                "ITEM_EXCLUDES",
                ITEM_EXCLUDES_MAX_LENGTH,
                value);
    }
}
