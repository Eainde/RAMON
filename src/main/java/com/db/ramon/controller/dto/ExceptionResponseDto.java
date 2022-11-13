package com.db.ramon.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.immutables.value.Value;

@Value.Immutable
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
// @JsonSerialize(as = ImmutableExceptionResponseDto.class)
public interface ExceptionResponseDto {
    int code();

    String description();
}
