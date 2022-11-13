package com.db.ramon.value.object;

import com.db.ramon.Wrapped;
import com.db.ramon.domain.Domain;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

@Value.Immutable
@Wrapped
public abstract class ReferenceToIsicRev4 extends Domain<String> {
    private static final int REFERENCE_TO_ISIC_REVISION_4_MAX_LENGTH = 100;
    private static final String REFERENCE_TO_ISIC_REVISION_4_MAX_LENGTH_VALIDATION_FAILED_MESSAGE =
            "%s must be less than or equals %s";

    @Value.Check
    public void check() {
        final String value = value();
        Preconditions.checkState(
                value.length() <= REFERENCE_TO_ISIC_REVISION_4_MAX_LENGTH,
                REFERENCE_TO_ISIC_REVISION_4_MAX_LENGTH_VALIDATION_FAILED_MESSAGE,
                "REFERENCE_TO_ISIC_REVISION_4",
                REFERENCE_TO_ISIC_REVISION_4_MAX_LENGTH,
                value);
    }
}
