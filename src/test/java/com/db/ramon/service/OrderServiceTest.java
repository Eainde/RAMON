package com.db.ramon.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableOrderAggregate;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.controller.dto.ImmutableOrderDto;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.domain.*;
import com.db.ramon.entity.OrderEntity;
import com.db.ramon.repositiory.OrderRepository;
import com.db.ramon.util.CsvUtil;
import com.db.ramon.value.object.ImmutableItemAlsoIncludes;
import com.db.ramon.value.object.ImmutableItemExcludes;
import com.db.ramon.value.object.ImmutableItemIncludes;
import com.db.ramon.value.object.ImmutableReferenceToIsicRev4;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService service;

    @MockBean
    private OrderRepository repository;

    @MockBean
    private CsvUtil util;

    @MockBean
    private Mapper<OrderAggregate, OrderEntity> mapper;

    @MockBean
    private Mapper<OrderAggregate, OrderDto> dtoMapper;

    private OrderAggregate orderAggregate;
    private OrderEntity entity;
    private List<OrderAggregate> orderAggregateList;
    private List<OrderDto> orderDtoList;

    @BeforeEach
    void setUp() {
        orderAggregateList = new ArrayList<>();
        orderDtoList = new ArrayList<>();

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
        orderAggregateList.add(orderAggregate);

        entity = new OrderEntity();
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

        OrderDto orderDto = ImmutableOrderDto.builder()
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
        orderDtoList.add(orderDto);
    }

    @DisplayName("Find all the orders")
    @Test
    void findAll() {
        Mockito.when(mapper.mapToDomain(entity)).thenReturn(orderAggregate);
        Mockito.when(repository.findAll()).thenReturn(orderAggregateList);
        assertNotNull(service.findAll());
        assertEquals(service.findAll().get(0).orderId().value(), 1);
    }

    @DisplayName("Find order by its ID")
    @Test
    void findById() {
        Mockito.when(mapper.mapToDomain(entity)).thenReturn(orderAggregate);
        Mockito.when(repository.findById(1)).thenReturn(orderAggregate);
        assertNotNull(service.findById(1));
        assertEquals(service.findById(1).orderId().value(), 1);
    }

    @DisplayName("Add order by passing the csv file")
    @Test
    void add() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("NACE.csv");
        MockMultipartFile file = new MockMultipartFile("NACE", "NACE.csv", MediaType.TEXT_PLAIN_VALUE, is);
        Mockito.when(util.convertCsvToDto(any())).thenReturn(orderDtoList);
        Mockito.when(repository.add(any())).thenReturn(true);
        assertTrue(service.add(file));
    }
}
