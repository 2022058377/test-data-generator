package com.example.testdatagenerator.dto.response;

import com.example.testdatagenerator.dto.TableSchemaDto;

public record SimpleTableSchemaResponse(
        String schemaName,
        String userId
) {

    public static SimpleTableSchemaResponse fromDto(TableSchemaDto dto) {
        return new SimpleTableSchemaResponse(
                dto.schemaName(), dto.userId()
        );
    }
}
