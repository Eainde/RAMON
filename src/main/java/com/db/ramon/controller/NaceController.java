package com.db.ramon.controller;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.controller.dto.NaceDto;
import com.db.ramon.service.NaceService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/nace")
public class NaceController {
  private final Mapper<NaceAggregate, NaceDto> mapper;
  private final NaceService service;

  NaceController(final NaceService service, final Mapper<NaceAggregate, NaceDto> mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping(value = "/", produces = "application/json")
  public ResponseEntity<List<NaceDto>> findAll() {
    return new ResponseEntity<>(
        service.findAll().stream().map(mapper::mapToEntity).collect(Collectors.toList()),
        HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<NaceDto> findById(@PathVariable("id") long id) {
    return new ResponseEntity<>(mapper.mapToEntity(service.findById(id)), HttpStatus.OK);
  }

  @PostMapping(value = "/", produces = "application/json")
  public ResponseEntity<Boolean> upload(@RequestParam("file") MultipartFile file)
      throws IOException {
    return new ResponseEntity<>(service.add(file), HttpStatus.OK);
  }
}
