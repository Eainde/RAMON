package com.db.ramon.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.db.ramon.controller.dto.mapper.OrderDtoMapper;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsvUtilTest {
  @Autowired private OrderDtoMapper mapper;
  @Autowired private CsvUtil csvUtil;

  @Test
  void csvToTutorials() {
    InputStream is = getClass().getClassLoader().getResourceAsStream("NACE.csv");
    assertEquals(csvUtil.convertCsvToDto(is).get(0).orderId(), 398481);
  }
}
