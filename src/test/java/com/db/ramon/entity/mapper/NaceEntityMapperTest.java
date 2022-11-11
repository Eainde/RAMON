package com.db.ramon.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableNaceAggregate;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.domain.*;
import com.db.ramon.entity.NaceEntity;
import com.db.ramon.value.object.ImmutableItemAlsoIncludes;
import com.db.ramon.value.object.ImmutableItemExcludes;
import com.db.ramon.value.object.ImmutableItemIncludes;
import com.db.ramon.value.object.ImmutableReferenceToIsicRev4;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NaceEntityMapperTest {
  private Mapper<NaceAggregate, NaceEntity> mapper;

  @BeforeEach
  public void setUp() {
    mapper = new NaceEntityMapper();
  }

  @Test
  void mapToDomain() {
    NaceEntity entity = new NaceEntity();
    entity.setOrderId(1L);
    entity.setLevel(1);
    entity.setCode("Code");
    entity.setParent("Parent");
    entity.setDescription("Description");
    entity.setItemIncludes("Item Includes");
    entity.setItemAlsoIncludes("Item Also Includes");
    entity.setItemExcludes("Item Excludes");
    entity.setRulings("Rulings");
    entity.setRefToIsicRev4("Ref 4");
    assertEquals(mapper.mapToDomain(entity).orderId().value(), 1);
  }

  @Test
  void mapToEntity() {
    NaceAggregate naceAggregate =
        ImmutableNaceAggregate.builder()
            .orderId(ImmutableOrderId.of(1L))
            .level(ImmutableLevel.of(1))
            .code(ImmutableCode.of("A"))
            .parent(ImmutableParent.of("Ko"))
            .description(ImmutableDescription.of("Description"))
            .itemIncludes(Optional.of("item includes").map(ImmutableItemIncludes::of))
            .itemAlsoIncludes(Optional.of("Item also includes").map(ImmutableItemAlsoIncludes::of))
            .rulings(Optional.of("Rulings").map(ImmutableRulings::of))
            .itemExcludes(Optional.of("Item excludes").map(ImmutableItemExcludes::of))
            .refToIsicRev4(ImmutableReferenceToIsicRev4.of("Rev 4"))
            .build();
    assertEquals(mapper.mapToEntity(naceAggregate).getOrderId(), 1);
  }
}
