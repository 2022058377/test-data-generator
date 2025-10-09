package com.example.testdatagenerator.dto.request;

import com.example.testdatagenerator.dto.TableSchemaDto;

import java.util.List;
import java.util.stream.Collectors;

public record TableSchemaRequest(
        String schemaName,
        String userid,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaRequest of(String schemaName, String userid, List<SchemaFieldRequest> schemaFields) {
        return new TableSchemaRequest(schemaName, userid, schemaFields);
    }

    public TableSchemaDto toDto() {
        return TableSchemaDto.of(
                this.schemaName,
                this.userid,
                null,
                this.schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
