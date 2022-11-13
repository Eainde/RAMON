package com.db.ramon.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableExceptionResponseDto.class)
public interface ExceptionResponseDto {
    int code();

    @Nullable
    String description();
}
