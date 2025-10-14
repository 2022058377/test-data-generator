package com.example.testdatagenerator.service.exporter;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.dto.TableSchemaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FileExporterContext {

    private final Map<ExportFileType, MockDataFileExporter> fileExporterMap;

    // @Component로 등록된 모든 MockDataFileExporter 구현체들을 생성자로 주입받아 맵으로 저장
    public FileExporterContext(List<MockDataFileExporter> fileExporters) {
        this.fileExporterMap = fileExporters.stream()
                .collect(Collectors.toMap(MockDataFileExporter::getType, Function.identity()));
    }

    public String export(ExportFileType type, TableSchemaDto dto, Integer rowCount) {
        MockDataFileExporter fileExporter = fileExporterMap.get(type);

        return fileExporter.export(dto, rowCount);
    }
}
