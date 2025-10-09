package com.example.testdatagenerator.dto.request;

import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.SchemaFieldDto;

public record SchemaFieldRequest(
        MockDataType mockDataType,
        String fieldName,
        Integer fieldOrder,
        Integer blankPercent,
        String typeOptionJson,
        String forceValue
) {

    public SchemaFieldDto toDto() {
        return SchemaFieldDto.of(
                this.fieldName,
                this.mockDataType,
                this.fieldOrder,
                this.blankPercent,
                this.typeOptionJson,
                this.forceValue
        );
    }
}
