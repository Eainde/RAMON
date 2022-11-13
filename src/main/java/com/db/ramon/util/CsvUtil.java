package com.db.ramon.util;

import com.db.ramon.constant.FileHeaderEnum;
import com.db.ramon.controller.dto.OrderDto;
import com.db.ramon.controller.dto.mapper.OrderDtoMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class CsvUtil {
    private final OrderDtoMapper mapper;

    CsvUtil(final OrderDtoMapper mapper) {
        this.mapper = mapper;
    }

    public List<OrderDto> convertCsvToDto(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(
                        fileReader,
                        CSVFormat.DEFAULT
                                .builder()
                                .setHeader(FileHeaderEnum.class)
                                .setSkipHeaderRecord(true)
                                .build()); ) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            return mapper.mapToDto(csvRecords);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
