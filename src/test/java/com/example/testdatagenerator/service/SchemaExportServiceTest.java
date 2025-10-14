package com.example.testdatagenerator.service;

import com.example.testdatagenerator.domain.TableSchema;
import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.dto.TableSchemaDto;
import com.example.testdatagenerator.repository.TableSchemaRepository;
import com.example.testdatagenerator.service.exporter.FileExporterContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("[Service] 스키마 파일 출력 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class SchemaExportServiceTest {

    @InjectMocks private SchemaExportService sut;

    @Mock private FileExporterContext fileExporterContext;
    @Mock private TableSchemaRepository tableSchemaRepository;

    @DisplayName("출력 파일 유형, 스키마 정보, 행 수가 주어지면, 엔티티 출력 여부를 마킹하고 가짜 데이터 파일을 유형에 맞게 반환한다.")
    @Test
    void givenExportFileTypeAndTableSchemaAndRowCount_whenExport_thenReturnsFileContent() {
        // Given
        ExportFileType type = ExportFileType.CSV;
        TableSchemaDto dto = TableSchemaDto.of("test-schema", "JaeWon", null, null);
        Integer rowCount = 10;
        TableSchema expectedTableSchema = TableSchema.of(dto.schemaName(), dto.userId());
        given(tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())).willReturn(Optional.of(expectedTableSchema));
        given(fileExporterContext.export(type, dto, rowCount)).willReturn("test,file,format");

        // When
        String result = sut.export(type, dto, rowCount);

        // Then
        assertThat(result).isEqualTo("test,file,format");
        assertThat(expectedTableSchema.isExported()).isTrue();
        then(tableSchemaRepository).should().findByUserIdAndSchemaName(dto.userId(), dto.schemaName());
        then(fileExporterContext).should().export(type, dto, rowCount);
    }

    @DisplayName("입력 파라미터 중에 유저 식별 정보가 없으면 스키마 테이블 조회를 시도하지 않는다.")
    @Test
    void givenNoUserIdParams_whenExport_thenDoesNotTrySelectingTableSchema() {
        // Given
        ExportFileType type = ExportFileType.CSV;
        String userId = null;
        TableSchemaDto dto = TableSchemaDto.of("test-schema", userId, null, null);
        Integer rowCount = 10;
        TableSchema expectedTableSchema = TableSchema.of(dto.schemaName(), dto.userId());
        given(fileExporterContext.export(type, dto, rowCount)).willReturn("test,file,format");

        // When
        String result = sut.export(type, dto, rowCount);

        // Then
        assertThat(result).isEqualTo("test,file,format");
        then(tableSchemaRepository).shouldHaveNoInteractions();
        then(fileExporterContext).should().export(type, dto, rowCount);
    }
}