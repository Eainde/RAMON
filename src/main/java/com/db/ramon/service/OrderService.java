package com.db.ramon.service;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.OrderAggregate;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.repositiory.OrderRepository;
import com.db.ramon.util.CsvUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrderService {
    private final OrderRepository repository;

    private final CsvUtil util;

    private final Mapper<OrderAggregate, OrderDto> dtoMapper;

    OrderService(final OrderRepository repository, CsvUtil util, Mapper<OrderAggregate, OrderDto> dtoMapper) {
        this.repository = repository;
        this.util = util;
        this.dtoMapper = dtoMapper;
    }

    public List<OrderAggregate> findAll() {
        return repository.findAll();
    }

    public OrderAggregate findById(long id) {
        return repository.findById(id);
    }

    public boolean add(MultipartFile file) throws IOException {
        List<OrderDto> orderDtos = util.convertCsvToDto(file.getInputStream());
        List<OrderAggregate> aggregates =
                orderDtos.stream().map(dtoMapper::mapToDomain).collect(Collectors.toList());
        repository.add(aggregates);
        return true;
    }
}
