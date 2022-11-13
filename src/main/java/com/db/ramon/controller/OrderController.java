package com.db.ramon.controller;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    private final Mapper<OrderAggregate, OrderDto> mapper;
    private final OrderService service;

    OrderController(final OrderService service, final Mapper<OrderAggregate, OrderDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all the orders")
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<OrderDto>> findAll() {
        return new ResponseEntity<>(
                service.findAll().stream().map(mapper::mapToEntity).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Get a order by its id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OrderDto> findById(@PathVariable("id") long id) {
        return new ResponseEntity<>(mapper.mapToEntity(service.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Add a new order")
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Boolean> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(service.add(file), HttpStatus.OK);
    }
}
