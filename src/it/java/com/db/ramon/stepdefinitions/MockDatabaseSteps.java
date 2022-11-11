package com.db.ramon.stepdefinitions;

import com.db.ramon.Mapper;
import com.db.ramon.aggregate.NaceAggregate;
import com.db.ramon.entity.NaceEntity;
import com.db.ramon.repositiory.NaceRepository;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.messages.internal.com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

public class MockDatabaseSteps {
  private final NaceRepository repository;

  private final Mapper<NaceAggregate, NaceEntity> mapper;
  private final NamedParameterJdbcTemplate jdbcTemplate;

  MockDatabaseSteps(
      final NaceRepository repository,
      Mapper<NaceAggregate, NaceEntity> mapper,
      final NamedParameterJdbcTemplate jdbcTemplate) {
    this.repository = repository;
    this.mapper = mapper;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Given("Setup database")
  public void setupDatabase() throws IOException {
    final var initSql =
        Resources.toString(Resources.getResource("database/INIT.sql"), StandardCharsets.UTF_8);
    jdbcTemplate.update(initSql, ImmutableMap.of());
  }

  @Given("Add following orders in database")
  public void initializeData(final DataTable dataTable) {
    final List<NaceEntity> records = dataTable.asList(NaceEntity.class);
    repository.add(records.stream().map(mapper::mapToDomain).collect(Collectors.toList()));
  }

  @DataTableType
  public NaceEntity orderEntry(final Map<String, String> table) {
    NaceEntity order = new NaceEntity();
    order.setOrderId(Long.parseLong(table.get("orderId")));
    order.setLevel(Integer.parseInt(table.get("_level")));
    order.setCode(table.get("code"));
    order.setParent(table.get("parent"));
    order.setDescription(table.get("description"));
    order.setItemIncludes(table.get("item_includes"));
    order.setItemAlsoIncludes(table.get("item_also_includes"));
    order.setItemExcludes(table.get("item_excludes"));
    order.setRulings(table.get("rulings"));
    order.setRefToIsicRev4(table.get("ref_to_isic_rev_4"));
    return order;
  }
}
