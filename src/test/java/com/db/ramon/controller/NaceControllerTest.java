package com.db.ramon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableNaceAggregate;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.controller.dto.NaceDto;
import com.db.ramon.domain.*;
import com.db.ramon.service.NaceService;
import com.db.ramon.value.object.ImmutableItemAlsoIncludes;
import com.db.ramon.value.object.ImmutableItemExcludes;
import com.db.ramon.value.object.ImmutableItemIncludes;
import com.db.ramon.value.object.ImmutableReferenceToIsicRev4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = NaceController.class)
// @TestPropertySource(locations = {"classpath:unitTest.properties"})
class NaceControllerTest {
  private MockMvc mockMvc;
  @Autowired private WebApplicationContext context;
  @MockBean private Mapper<NaceAggregate, NaceDto> mapper;
  @MockBean private NaceService service;
  private NaceAggregate naceAggregate;
  private List<NaceAggregate> naceAggregateList;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    naceAggregateList = new ArrayList<>();

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
  }

  @Test
  void findAll() throws Exception {
    Mockito.when(service.findAll()).thenReturn(naceAggregateList);
    mockMvc
        .perform(get("/nace/"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andDo(MockMvcResultHandlers.print());
  }

  @ParameterizedTest
  @ValueSource(ints = 1)
  void findById(int id) throws Exception {
    Mockito.when(service.findById(id)).thenReturn(naceAggregate);
    mockMvc
        .perform(get("/nace/{id}", id))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void upload() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile(
            "file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    Mockito.when(service.add(file)).thenReturn(true);
    mockMvc.perform(multipart("/nace/").file(file)).andExpect(status().isOk());
  }
}
