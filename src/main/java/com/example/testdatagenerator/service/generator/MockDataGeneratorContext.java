package com.example.testdatagenerator.service.generator;

import com.example.testdatagenerator.domain.constant.MockDataType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MockDataGeneratorContext {

    private final Map<MockDataType, MockDataGenerator> generatorMap;

    public MockDataGeneratorContext(List<MockDataGenerator> generators) {
        this.generatorMap = generators.stream()
                .collect(Collectors.toMap(MockDataGenerator::getType, Function.identity()));
    }

    public String generate(MockDataType type, Integer blankPercent, String typeOptionJson, String forceValue) {
        MockDataGenerator generator = generatorMap.get(type);

        // 다양한 가짜 데이터 생성기가 도입되기 전, 기본 데이터 생성기 설정
        if(generator == null) {
            return generatorMap.get(MockDataType.STRING)
                    .generate(blankPercent, "", forceValue);
        }

        return generator.generate(blankPercent, typeOptionJson, forceValue);
    }
}
