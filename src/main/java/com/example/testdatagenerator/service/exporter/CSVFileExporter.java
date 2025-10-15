package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.service.generator.MockDataGeneratorContext;
import org.springframework.stereotype.Component;

@Component
public class CSVFileExporter extends DelimiterBasedFileExporter {

    public CSVFileExporter(MockDataGeneratorContext context) {
        super(context);
    }

    @Override
    public ExportFileType getType() {
        return ExportFileType.CSV;
    }

    @Override
    public String getDelimiter() {
        return ",";
    }

}
