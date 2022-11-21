package com.db.ramon.util;

import com.db.ramon.controller.dto.mapper.OrderDtoMapper;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvUtilTest {
    @Autowired
    private OrderDtoMapper mapper;

    @Autowired
    private CsvUtil csvUtil;

    @Test
    void convert_file_to_dto() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("NACE.csv");
        assertNotNull(csvUtil.convertCsvToDto(is));
    }
    @Test
    void should_have_order_id() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("NACE.csv");
        assertEquals(csvUtil.convertCsvToDto(is).get(0).orderId(), 398481);
    }

    @Test
    void should_not_have_order_id() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("NACE.csv");
        assertNotEquals(csvUtil.convertCsvToDto(is).get(0).orderId(), 3984);
    }
}
