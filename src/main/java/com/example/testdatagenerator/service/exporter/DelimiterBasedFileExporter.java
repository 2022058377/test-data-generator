package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.dto.SchemaFieldDto;
import com.example.testdatagenerator.dto.TableSchemaDto;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DelimiterBasedFileExporter implements MockDataFileExporter {

    public abstract String getDelimiter();

    @Override
    public String export(TableSchemaDto dto, Integer rowCount) {
        StringBuilder sb = new StringBuilder();

        // 헤더
        sb.append(dto.schemaFields().stream()
                .sorted(Comparator.comparing(SchemaFieldDto::fieldOrder))
                .map(SchemaFieldDto::fieldName)
                .collect(Collectors.joining(getDelimiter()))
        );
        sb.append("\n");

        // 데이터
        IntStream.range(0, rowCount).forEach(i -> {
            sb.append(dto.schemaFields().stream()
                    .sorted(Comparator.comparing(SchemaFieldDto::fieldOrder))
                    .map(field -> "가짜-데이터")
                    .map(v -> v == null ? "" : v)
                    .collect(Collectors.joining(getDelimiter()))
            );
            sb.append("\n");
        });

        return sb.toString();
    }
}
