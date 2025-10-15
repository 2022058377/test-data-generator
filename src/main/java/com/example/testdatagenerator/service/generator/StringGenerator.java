package com.example.testdatagenerator.service.generator;

import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.MockDataDto;
import com.example.testdatagenerator.repository.MockDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class StringGenerator implements MockDataGenerator {

    private final MockDataRepository mockDataRepository;
    private final ObjectMapper objectMapper;

    @Override
    public MockDataType getType() {
        return MockDataType.STRING;
    }

    @Override
    public String generate(Integer blankPercent, String typeOptionJson, String forceValue) {

        RandomGenerator randomGenerator = RandomGenerator.getDefault();

        if(randomGenerator.nextInt(100) < blankPercent) {
            return null;
        }

        if(forceValue != null && !forceValue.isBlank()) {
            return forceValue;
        }

        Option option = new Option(1, 10); // 기본값
        try {
            if (typeOptionJson != null && !typeOptionJson.isBlank()) {
                option = objectMapper.readValue(typeOptionJson, Option.class);
            }
        } catch (JsonProcessingException jpe) {
            log.warn("Json 옵션 정보를 읽어들이는데 실패했습니다. 기본 옵션으로 동작합니다. - 입력 옵션: {}, 필요 옵션 예: {}",
                    typeOptionJson, option);
        }

        if(option.minLength() < 1) {
            throw new IllegalArgumentException("[가짜 데이터 생성 옵션 오류] 최소 길이가 1보다 작습니다. - option: " + typeOptionJson);
        } else if(option.maxLength() > 10) {
            throw new IllegalArgumentException("[가짜 데이터 생성 옵션 오류] 최대 길이가 10보다 큽니다. - option: " + typeOptionJson);
        } else if(option.minLength() > option.maxLength()) {
            throw new IllegalArgumentException("[가짜 데이터 생성 옵션 오류] 최소 길이가 최대 길이보다 큽니다. - option: " + typeOptionJson);
        }

        List<MockDataDto> mockDataList = mockDataRepository.findByMockDataType(getType()).stream()
                .map(MockDataDto::from)
                .toList();

        String body = mockDataList.stream()
                .map(MockDataDto::mockDataValue)
                .collect(Collectors.joining(""))
                .replaceAll("[^가-힣]", "");

        int diff = option.maxLength - option.minLength;
        int point = randomGenerator.nextInt(body.length() - option.maxLength);
        int offset = (diff < 1 ? diff : randomGenerator.nextInt(diff)) + option.minLength;

        return body.substring(point, point + offset);
    }

    public record Option(Integer minLength, Integer maxLength) {}
}
