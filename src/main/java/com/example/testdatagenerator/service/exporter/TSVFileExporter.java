package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import org.springframework.stereotype.Component;

@Component
public class TSVFileExporter extends DelimiterBasedFileExporter {

    @Override
    public ExportFileType getType() {
        return ExportFileType.TSV;
    }

    @Override
    public String getDelimiter() {
        return "\t";
    }
}
