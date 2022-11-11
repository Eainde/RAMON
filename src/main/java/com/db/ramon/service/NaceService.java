package com.db.ramon.service;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.controller.dto.NaceDto;
import com.db.ramon.entity.NaceEntity;
import com.db.ramon.repositiory.NaceRepository;
import com.db.ramon.util.CsvUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NaceService {
  private final NaceRepository repository;

  private final CsvUtil util;
  private final Mapper<NaceAggregate, NaceEntity> mapper;

  private final Mapper<NaceAggregate, NaceDto> dtoMapper;

  NaceService(
      final NaceRepository repository,
      CsvUtil util,
      final Mapper<NaceAggregate, NaceEntity> mapper,
      Mapper<NaceAggregate, NaceDto> dtoMapper) {
    this.repository = repository;
    this.util = util;
    this.mapper = mapper;
    this.dtoMapper = dtoMapper;
  }

  public List<NaceAggregate> findAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(Collectors.toList());
  }

  public NaceAggregate findById(long id) {
    return mapper.mapToDomain(repository.findById(id));
  }

  public boolean add(MultipartFile file) throws IOException {
    List<NaceDto> naceDtos = util.convertCsvToDto(file.getInputStream());
    List<NaceAggregate> aggregates =
        naceDtos.stream().map(dtoMapper::mapToDomain).collect(Collectors.toList());
    repository.add(aggregates);
    return true;
  }
}
