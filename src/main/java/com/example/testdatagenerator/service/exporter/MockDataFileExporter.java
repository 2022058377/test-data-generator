package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.dto.TableSchemaDto;

public interface MockDataFileExporter {

    ExportFileType getType();

    String export(TableSchemaDto dto, Integer rowCount);

}
