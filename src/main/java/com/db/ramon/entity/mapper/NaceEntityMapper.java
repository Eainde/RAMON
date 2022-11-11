package com.db.ramon.entity.mapper;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableNaceAggregate;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.domain.*;
import com.db.ramon.entity.NaceEntity;
import com.db.ramon.value.object.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class NaceEntityMapper implements Mapper<NaceAggregate, NaceEntity> {
  @Override
  public NaceAggregate mapToDomain(NaceEntity entity) {
    return ImmutableNaceAggregate.builder()
        .orderId(ImmutableOrderId.of(entity.getOrderId()))
        .level(ImmutableLevel.of(entity.getLevel()))
        .code(ImmutableCode.of(entity.getCode()))
        .parent(ImmutableParent.of(entity.getParent()))
        .description(ImmutableDescription.of(entity.getDescription()))
        .itemIncludes(Optional.ofNullable(entity.getItemIncludes()).map(ImmutableItemIncludes::of))
        .itemAlsoIncludes(
            Optional.ofNullable(entity.getItemAlsoIncludes()).map(ImmutableItemAlsoIncludes::of))
        .rulings(Optional.ofNullable(entity.getRulings()).map(ImmutableRulings::of))
        .itemExcludes(Optional.ofNullable(entity.getItemExcludes()).map(ImmutableItemExcludes::of))
        .refToIsicRev4(ImmutableReferenceToIsicRev4.of(entity.getRefToIsicRev4()))
        .build();
  }

  @Override
  public NaceEntity mapToEntity(NaceAggregate domain) {
    NaceEntity entity = new NaceEntity();
    entity.setOrderId(domain.orderId().value());
    entity.setLevel(domain.level().value());
    entity.setCode(domain.code().value());
    entity.setParent(domain.parent().value());
    entity.setDescription(domain.description().value());
    entity.setItemIncludes(domain.itemIncludes().map(ItemIncludes::value).orElse(null));
    entity.setItemAlsoIncludes(domain.itemAlsoIncludes().map(ItemAlsoIncludes::value).orElse(null));
    entity.setItemExcludes(domain.itemExcludes().map(ItemExcludes::value).orElse(null));
    entity.setRulings(domain.rulings().map(Rulings::value).orElse(null));
    entity.setRefToIsicRev4(domain.refToIsicRev4().value());
    return entity;
  }
}
