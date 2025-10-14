package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.SchemaFieldDto;
import com.example.testdatagenerator.dto.TableSchemaDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("[IntegrationTest] 파일 출력기 컨텍스트 테스트")
@SpringBootTest
record FileExporterContextTest(@Autowired FileExporterContext sut) {

    @DisplayName("파일 형식, 테이블 스키마, 행 수가 주어지면, 파일 형식에 맞게 변환한 문자열을 리턴한다.")
    @Test
    void givenFileTypeAndTableSchemaAndRowCount_whenExport_thenReturnsFormattedString() {
        // Given
        ExportFileType type = ExportFileType.CSV;
        TableSchemaDto dto = TableSchemaDto.of(
                "test-schema",
                "JaeWon",
                null,
                Set.of(
                        SchemaFieldDto.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldDto.of("age", MockDataType.NUMBER, 3, 0, null, null),
                        SchemaFieldDto.of("name", MockDataType.NAME, 2, 0, null, null),
                        SchemaFieldDto.of("created_at", MockDataType.DATETIME, 5, 0, null, null),
                        SchemaFieldDto.of("car", MockDataType.CAR, 4, 0, null, null)
                )
        );
        Integer rowCount = 10;

        // When
        String result = sut.export(type, dto, rowCount);

        // Then
        System.out.println(result); // 관찰용
        assertThat(result).startsWith("id,name,age,car,created_at");
    }
}