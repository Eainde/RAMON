package com.db.ramon.controller.dto.mapper;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableOrderAggregate;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.controller.dto.ImmutableOrderDto;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.domain.*;
import com.db.ramon.value.object.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper implements Mapper<OrderAggregate, OrderDto> {
  @Override
  public OrderAggregate mapToDomain(OrderDto dto) {
    return ImmutableOrderAggregate.builder()
        .orderId(ImmutableOrderId.of(dto.orderId()))
        .level(ImmutableLevel.of(dto.level()))
        .code(ImmutableCode.of(dto.code()))
        .parent(ImmutableParent.of(dto.parent()))
        .description(ImmutableDescription.of(dto.description()))
        .itemIncludes(Optional.ofNullable(dto.itemIncludes()).map(ImmutableItemIncludes::of))
        .itemAlsoIncludes(
            Optional.ofNullable(dto.itemAlsoIncludes()).map(ImmutableItemAlsoIncludes::of))
        .rulings(Optional.ofNullable(dto.rulings()).map(ImmutableRulings::of))
        .itemExcludes(Optional.ofNullable(dto.itemExcludes()).map(ImmutableItemExcludes::of))
        .refToIsicRev4(ImmutableReferenceToIsicRev4.of(dto.refToIsicRev4()))
        .build();
  }

  @Override
  public OrderDto mapToEntity(OrderAggregate domain) {
    return ImmutableOrderDto.builder()
        .orderId(domain.orderId().value())
        .level(domain.level().value())
        .code(domain.code().value())
        .parent(domain.parent().value())
        .description(domain.description().value())
        .itemIncludes(domain.itemIncludes().map(ItemIncludes::value).orElse(null))
        .itemAlsoIncludes(domain.itemAlsoIncludes().map(ItemAlsoIncludes::value).orElse(null))
        .rulings(domain.rulings().map(Rulings::value).orElse(null))
        .itemExcludes(domain.itemExcludes().map(ItemExcludes::value).orElse(null))
        .refToIsicRev4(domain.refToIsicRev4().value())
        .build();
  }

  public List<OrderDto> mapToDto(List<CSVRecord> csvRecords) {
    List<OrderDto> result = new ArrayList<>();
    csvRecords.forEach(
        record -> {
          OrderDto orderDto =
              ImmutableOrderDto.builder()
                  .orderId(Long.parseLong(record.get(0)))
                  .level(Integer.parseInt(record.get(1)))
                  .code(record.get(2))
                  .parent(record.get(3))
                  .description(record.get(4))
                  .itemIncludes(record.get(5))
                  .itemAlsoIncludes(record.get(6))
                  .rulings(record.get(7))
                  .itemExcludes(record.get(8))
                  .refToIsicRev4(record.get(9))
                  .build();
          result.add(orderDto);
        });
    return result;
  }
}
