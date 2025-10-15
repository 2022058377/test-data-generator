package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.dto.SchemaFieldDto;
import com.example.testdatagenerator.dto.TableSchemaDto;
import com.example.testdatagenerator.service.generator.MockDataGeneratorContext;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class DelimiterBasedFileExporter implements MockDataFileExporter {

    private final MockDataGeneratorContext context;

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
                    .map(field -> context.generate(
                            field.mockDataType(),
                            field.blankPercent(),
                            field.typeOptionJson(),
                            field.forceValue()
                    ))
                    .map(v -> v == null ? "" : v)
                    .collect(Collectors.joining(getDelimiter()))
            );
            sb.append("\n");
        });

        return sb.toString();
    }
}
