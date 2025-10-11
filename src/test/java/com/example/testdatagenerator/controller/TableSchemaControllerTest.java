package com.example.testdatagenerator.controller;

import com.example.testdatagenerator.config.SecurityConfig;
import com.example.testdatagenerator.domain.constant.ExportFileType;
import com.example.testdatagenerator.domain.constant.MockDataType;
import com.example.testdatagenerator.dto.request.SchemaFieldRequest;
import com.example.testdatagenerator.dto.request.TableSchemaExportRequest;
import com.example.testdatagenerator.dto.request.TableSchemaRequest;
import com.example.testdatagenerator.util.FormDataEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[Controller] 테이블 스키마 컨트롤러 테스트")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(TableSchemaController.class)
public record TableSchemaControllerTest(
        @Autowired MockMvc mvc,
        @Autowired FormDataEncoder formDataEncoder,
        @Autowired ObjectMapper objectMapper
) {

    @DisplayName("[GET] 테이블 스키마 페이지 -> 테이블 스키마 뷰 (정상)")
    @Test
    void givenNothing_whenRequesting_thenShowTableSchemaView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/table-schema"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                .andExpect(model().attributeExists("mockDataTypes"))
                .andExpect(model().attributeExists("fileTypes"))
                .andExpect(view().name("table-schema"));

    }

    @DisplayName("[GET] 테이블 스키마 조회, 로그인 + 특정 테이블 스키마 (정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenRequesting_thenShowTableSchemaView() throws Exception {
        // given
        var schemaName = "test_schema";

        // when & then
        mvc.perform(get("/table-schema").queryParam("schemaName", schemaName))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                //.andExpect(model().attribute("tableSchema", hasProperty("schemaName", is(schemaName))))
                .andExpect(model().attributeExists("mockDataTypes"))
                .andExpect(model().attributeExists("fileTypes"))
                .andExpect(content().string(containsString(schemaName)))
                .andExpect(view().name("table-schema"));

    }

    @DisplayName("[POST] 테이블 스키마 생성, 변경 (정상)")
    @Test
    void givenTableSchemaRequest_whenCreatingOrUpdating_thenRedirectsToTableSchemaView() throws Exception {
        // given
        TableSchemaRequest request = TableSchemaRequest.of(
                "test_schema",
                "jaewon",
                List.of(
                        SchemaFieldRequest.of(
                                "id", MockDataType.ROW_NUMBER,  1, 0, null, null),
                        SchemaFieldRequest.of(
                                "name", MockDataType.NAME, 2, 10, null, null),
                        SchemaFieldRequest.of(
                                "age", MockDataType.NUMBER,  3, 20, null, null)
                )
        );

        // when & then
        mvc.perform(post("/table-schema")
                        .content(formDataEncoder.encode(request))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("tableSchemaRequest", request))
                .andExpect(redirectedUrl("/table-schema"));

    }

    @DisplayName("[GET] 내 스키마 목록 페이지 -> 내 스키마 목록 뷰 (정상)")
    @Test
    void givenAuthenticatedUser_whenRequesting_thenShowMySchemaView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/table-schema/my-schemas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchemas"))
                .andExpect(view().name("my-schemas"));

    }

    @DisplayName("[POST] 내 스키마 삭제 (정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenDeleting_thenRedirectsToTableSchemaView() throws Exception {
        // given
        String schemaName = "test_schema";

        // when & then
        mvc.perform(post("/table-schema/my-schemas/{schemaName}", schemaName)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my-schemas"));

    }

    @DisplayName("[GET] 테이블 스키마 파일 다운로드 -> 테이블 스키마 파일 (정상)")
    @Test
    void givenTableSchema_whenDownloading_thenReturnsFile() throws Exception {
        // given
        TableSchemaExportRequest request = TableSchemaExportRequest.of(
                "test_schema",
                100,
                ExportFileType.JSON,
                List.of(
                        SchemaFieldRequest.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldRequest.of("row1", MockDataType.STRING, 2, 0, "option", "well"),
                        SchemaFieldRequest.of("age", MockDataType.NUMBER, 3, 20, null, null)
                )
        );

        String queryParam = formDataEncoder.encode(request, false);

        // when & then
        mvc.perform(get("/table-schema/export?" + queryParam))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table-schema.txt"))
                .andExpect(content().json(objectMapper.writeValueAsString(request)));


    }
}
