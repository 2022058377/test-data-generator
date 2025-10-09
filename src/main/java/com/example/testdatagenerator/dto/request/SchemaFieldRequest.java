package com.example.testdatagenerator.dto.request;

import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.SchemaFieldDto;

public record SchemaFieldRequest(
        String fieldName,
        MockDataType mockDataType,
        Integer fieldOrder,
        Integer blankPercent,
        String typeOptionJson,
        String forceValue
) {

    public static SchemaFieldRequest of(
            String fieldName, MockDataType mockDataType, Integer fieldOrder, Integer blankPercent, String typeOptionJson, String forceValue)
    {
        return new SchemaFieldRequest(
                fieldName,
                mockDataType,
                fieldOrder,
                blankPercent,
                typeOptionJson,
                forceValue
        );
    }

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
