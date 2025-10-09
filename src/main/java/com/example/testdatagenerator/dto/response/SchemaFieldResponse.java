package com.example.testdatagenerator.dto.response;

import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.SchemaFieldDto;

public record SchemaFieldResponse(
        MockDataType mockDataType,
        String fieldName,
        Integer fieldOrder,
        Integer blankPercent,
        String typeOptionJson,
        String forceValue
) {

    public static SchemaFieldResponse fromDto(SchemaFieldDto dto) {
        return new SchemaFieldResponse(
                dto.mockDataType(),
                dto.fieldName(),
                dto.fieldOrder(),
                dto.blankPercent(),
                dto.typeOptionJson(),
                dto.forceValue()
        );
    }
}
