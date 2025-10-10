package com.example.testdatagenerator.dto.request;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.dto.TableSchemaDto;

import java.util.List;
import java.util.stream.Collectors;

public record TableSchemaExportRequest(
        String schemaName,
        Integer rowCount,
        ExportFileType fileType,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaExportRequest of(String schemaName, Integer rowCount, ExportFileType fileType, List<SchemaFieldRequest> schemaFields) {
        return new TableSchemaExportRequest(schemaName, rowCount, fileType, schemaFields);
    }

    public TableSchemaDto toDto(String userid) {
        return TableSchemaDto.of(
                this.schemaName,
                userid,
                null,
                this.schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
