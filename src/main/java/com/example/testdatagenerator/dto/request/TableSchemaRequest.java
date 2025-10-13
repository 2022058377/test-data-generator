package com.example.testdatagenerator.dto.request;

import com.example.testdatagenerator.dto.TableSchemaDto;

import java.util.List;
import java.util.stream.Collectors;

public record TableSchemaRequest(
        String schemaName,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaRequest of(String schemaName, List<SchemaFieldRequest> schemaFields) {
        return new TableSchemaRequest(schemaName, schemaFields);
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
