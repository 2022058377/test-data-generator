package com.example.testdatagenerator.controller;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.request.TableSchemaExportRequest;
import com.example.testdatagenerator.dto.request.TableSchemaRequest;
import com.example.testdatagenerator.dto.response.SchemaFieldResponse;
import com.example.testdatagenerator.dto.response.TableSchemaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TableSchemaController {

    private final ObjectMapper objectMapper;

    @GetMapping("/table-schema")
    public String tableSchema(Model model) {
        var TableSchema = defaultTableSchema();

        model.addAttribute("tableSchema", TableSchema);
        model.addAttribute("mockDataTypes", MockDataType.toObjects());
        model.addAttribute("fileTypes", Arrays.stream(ExportFileType.values()).toList());

        return "table-schema";
    }

    @PostMapping("/table-schema")
    public String createOrUpdateTableSchema(
            TableSchemaRequest request,
            RedirectAttributes attributes
    ) {
        attributes.addFlashAttribute(
                "tableSchemaRequest", request
        );

        return "redirect:/table-schema";
    }

    @GetMapping("/table-schema/my-schemas")
    public String mySchema() {
        return "my-schemas";
    }

    @PostMapping("/table-schema/my-schemas/{schemaName}")
    public String deleteMySchema(
            @PathVariable String schemaName
    ) {
        return "redirect:/my-schemas";
    }

    @GetMapping("/table-schema/export")
    public ResponseEntity<String> exportTableSchema(TableSchemaExportRequest request) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table-schema.txt")
                .body(json(request));
    }

    private TableSchemaResponse defaultTableSchema() {
        return new TableSchemaResponse(
                "schema_name",
                "JAEWON",
                List.of(
                        new SchemaFieldResponse("fieldName1", MockDataType.STRING, 1, 0, null, null),
                        new SchemaFieldResponse("fieldName1", MockDataType.NUMBER, 2, 0, null, null),
                        new SchemaFieldResponse("fieldName1", MockDataType.NAME, 3, 0, null, null)
                )
        );
    }

    private String json(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }
}
