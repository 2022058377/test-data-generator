package com.example.testdatagenerator.service;

import com.example.testdatagenerator.domain.TableSchema;
import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.dto.TableSchemaDto;
import com.example.testdatagenerator.repository.TableSchemaRepository;
import com.example.testdatagenerator.service.exporter.FileExporterContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchemaExportService {

    private final FileExporterContext fileExporterContext;
    private final TableSchemaRepository tableSchemaRepository;

    public String export(ExportFileType type, TableSchemaDto dto, Integer rowCount) {
        if(dto.userId() != null) {
            tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())
                    .ifPresent(TableSchema::markExported);
        }

        return fileExporterContext.export(type, dto, rowCount);
    }
}
