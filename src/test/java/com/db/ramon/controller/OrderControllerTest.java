package com.db.ramon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.ImmutableOrderAggregate;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.domain.*;
import com.db.ramon.service.OrderService;
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

@WebMvcTest(controllers = OrderController.class)
// @TestPropertySource(locations = {"classpath:unitTest.properties"})
class OrderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private Mapper<OrderAggregate, OrderDto> mapper;

    @MockBean
    private OrderService service;

    private OrderAggregate orderAggregate;
    private List<OrderAggregate> orderAggregateList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        orderAggregateList = new ArrayList<>();

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
    }

    @Test
    void find_all_orders() throws Exception {
        Mockito.when(service.findAll()).thenReturn(orderAggregateList);
        mockMvc.perform(get("/v1/order/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }

    @ParameterizedTest
    @ValueSource(ints = 1)
    void find_order_by_ID(int id) throws Exception {
        Mockito.when(service.findById(id)).thenReturn(orderAggregate);
        mockMvc.perform(get("/v1/order/{id}", id)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void upload_file() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        Mockito.when(service.add(file)).thenReturn(true);
        mockMvc.perform(multipart("/v1/order/").file(file)).andExpect(status().isOk());
    }
}
