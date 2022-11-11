package com.db.ramon.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableNaceDto.class)
@JsonDeserialize(as = ImmutableNaceDto.class)
public interface NaceDto {

  long orderId();

  int level();

  String code();

  String parent();

  String description();

  @Nullable
  String itemIncludes();

  @Nullable
  String itemAlsoIncludes();

  @Nullable
  String rulings();

  @Nullable
  String itemExcludes();

  String refToIsicRev4();
}
