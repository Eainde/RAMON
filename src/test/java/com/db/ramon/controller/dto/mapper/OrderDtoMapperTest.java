package com.db.ramon.controller.dto.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.db.ramon.aggregate.ImmutableOrderAggregate;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.constant.FileHeaderEnum;
import com.db.ramon.controller.dto.ImmutableOrderDto;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.domain.*;
import com.db.ramon.value.object.ImmutableItemAlsoIncludes;
import com.db.ramon.value.object.ImmutableItemExcludes;
import com.db.ramon.value.object.ImmutableItemIncludes;
import com.db.ramon.value.object.ImmutableReferenceToIsicRev4;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderDtoMapperTest {
    private OrderAggregate orderAggregate;

    private OrderDto orderDto;
    private OrderDtoMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new OrderDtoMapper();
        orderAggregate = ImmutableOrderAggregate.builder()
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

        orderDto = ImmutableOrderDto.builder()
                .orderId(1)
                .level(1)
                .code("code")
                .parent("parent")
                .description("description")
                .itemIncludes("Item Includes")
                .itemAlsoIncludes("Item Also Includes")
                .rulings("rulings")
                .itemExcludes("item Excludes")
                .refToIsicRev4("rev 4")
                .build();
    }

    @Test
    void mapToDomain() {
        assertEquals(mapper.mapToDomain(orderDto).orderId().value(), 1L);
    }

    @Test
    void mapToEntity() {
        assertEquals(mapper.mapToEntity(orderAggregate).orderId(), 1);
    }

    @Test
    void testCSVRecordNULLValues() throws IOException {
        List<CSVRecord> csvRecords = new ArrayList<>();
        CSVRecord record;
        String[] values = new String[] {"1", "1", "A", "B", "C", "D", "E", "F", "G", "H"};
        final String rowData = StringUtils.join(values, ',');
        try (final CSVParser parser = CSVFormat.DEFAULT
                .builder()
                .setHeader(FileHeaderEnum.class)
                .build()
                .parse(new StringReader(rowData))) {
            record = parser.iterator().next();
            csvRecords.add(record);
        }
        mapper.mapToDto(csvRecords);
        assertEquals(values[0], csvRecords.get(0).get(0));
        assertEquals(values[1], csvRecords.get(0).get(1));
        assertEquals(values[2], csvRecords.get(0).get(2));
    }
}
