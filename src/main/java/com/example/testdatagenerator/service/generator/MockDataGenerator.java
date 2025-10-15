package com.example.testdatagenerator.service.generator;

import com.example.testdatagenerator.domain.constant.MockDataType;

public interface MockDataGenerator {

    MockDataType getType();

    String generate(Integer blankPercent, String typeOptionJson, String forceValue);
}
