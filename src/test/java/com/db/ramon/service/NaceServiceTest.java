package com.db.ramon.service;

import static org.junit.jupiter.api.Assertions.*;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableNaceAggregate;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.controller.dto.ImmutableNaceDto;
import com.db.ramon.controller.dto.NaceDto;
import com.db.ramon.domain.*;
import com.db.ramon.entity.NaceEntity;
import com.db.ramon.repositiory.NaceRepository;
import com.db.ramon.util.CsvUtil;
import com.db.ramon.value.object.ImmutableItemAlsoIncludes;
import com.db.ramon.value.object.ImmutableItemExcludes;
import com.db.ramon.value.object.ImmutableItemIncludes;
import com.db.ramon.value.object.ImmutableReferenceToIsicRev4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class NaceServiceTest {
  @Autowired private NaceService service;
  @MockBean private NaceRepository repository;
  @MockBean private CsvUtil util;
  @MockBean private Mapper<NaceAggregate, NaceEntity> mapper;
  @MockBean private Mapper<NaceAggregate, NaceDto> dtoMapper;
  private NaceAggregate naceAggregate;
  private NaceEntity entity;
  private List<NaceAggregate> naceAggregateList;
  private List<NaceDto> naceDtoList;

  @BeforeEach
  void setUp() {
    naceAggregateList = new ArrayList<>();
    naceDtoList = new ArrayList<>();

    naceAggregate =
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
    naceAggregateList.add(naceAggregate);

    entity = new NaceEntity();
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

    NaceDto naceDto =
        ImmutableNaceDto.builder()
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
    naceDtoList.add(naceDto);
  }

  @Test
  void findAll() {
    Mockito.when(mapper.mapToDomain(entity)).thenReturn(naceAggregate);
    Mockito.when(repository.findAll()).thenReturn(naceAggregateList);
    assertNotNull(service.findAll());
    assertEquals(service.findAll().get(0).orderId().value(), 1);
  }

  @Test
  void findById() {
    Mockito.when(mapper.mapToDomain(entity)).thenReturn(naceAggregate);
    Mockito.when(repository.findById(1)).thenReturn(naceAggregate);
    assertNotNull(service.findById(1));
    assertEquals(service.findById(1).orderId().value(), 1);
  }

  @Test
  void add() throws IOException {
    MockMultipartFile file =
        new MockMultipartFile(
            "file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    Mockito.when(repository.add(naceAggregateList)).thenReturn(true);
    Mockito.when(util.convertCsvToDto(file.getInputStream())).thenReturn(naceDtoList);
    assertTrue(service.add(file));
  }
}
