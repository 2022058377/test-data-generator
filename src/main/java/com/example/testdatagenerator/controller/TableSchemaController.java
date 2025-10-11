package com.example.testdatagenerator.controller;

import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.request.TableSchemaExportRequest;
import com.example.testdatagenerator.dto.request.TableSchemaRequest;
import com.example.testdatagenerator.dto.response.SchemaFieldResponse;
import com.example.testdatagenerator.dto.response.SimpleTableSchemaResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TableSchemaController {

    private final ObjectMapper objectMapper;

    @GetMapping("/table-schema")
    public String tableSchema(
            @RequestParam(required = false) String schemaName,
            Model model)
    {
        var tableSchema = defaultTableSchema(schemaName);

        model.addAttribute("tableSchema", tableSchema);
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
    public String mySchema(
            Model model
    ) {
        var TableSchemas = mySampleSchemas();

        model.addAttribute("tableSchemas", TableSchemas);

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

    private TableSchemaResponse defaultTableSchema(String schemaName) {
        return new TableSchemaResponse(
                schemaName != null ? schemaName : "schema_name",
                "JaeWon",
                List.of(
                        new SchemaFieldResponse("id", MockDataType.STRING, 1, 0, null, null),
                        new SchemaFieldResponse("name", MockDataType.NAME, 2, 10, null, null),
                        new SchemaFieldResponse("age", MockDataType.NUMBER, 3, 20, null, null),
                        new SchemaFieldResponse("my_car", MockDataType.CAR, 4, 50, null, null)
                )
        );
    }

    private List<SimpleTableSchemaResponse> mySampleSchemas() {
        return List.of(
                new SimpleTableSchemaResponse("schema_name1", "JaeWon", LocalDate.of(2025, 10, 10).atStartOfDay()),
                new SimpleTableSchemaResponse("schema_name2", "JaeWon", LocalDate.of(2025, 10, 11).atStartOfDay()),
                new SimpleTableSchemaResponse("schema_name3", "JaeWon", LocalDate.of(2025, 10, 12).atStartOfDay())
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
