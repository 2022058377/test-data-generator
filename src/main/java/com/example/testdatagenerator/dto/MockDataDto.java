package com.example.testdatagenerator.dto;

import com.example.testdatagenerator.domain.MockData;
import com.example.testdatagenerator.domain.constant.MockDataType;

public record MockDataDto(
        Long id,
        MockDataType mockDataType,
        String mockDataValue) {

    public static MockDataDto from(MockData entity) {
        return new MockDataDto(
                entity.getId(),
                entity.getMockDataType(),
                entity.getMockDataValue());
    }
}
