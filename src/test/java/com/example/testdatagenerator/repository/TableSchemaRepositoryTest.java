package com.example.testdatagenerator.repository;

import com.example.testdatagenerator.domain.TableSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository] 테이블 스키마 쿼리 테스트")
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TableSchemaRepositoryTest {

    @Autowired private TableSchemaRepository sut;

    @DisplayName("사용자별 테이블 스키마를 조회하면, 페이징된 테이블 스키마를 반환한다.")
    @Test
    void givenUserId_whenSelectingTableSchemas_thenReturnsPagedTableSchema() {
        // given
        var userId = "djkeh";

        // when
        Page<TableSchema> result = sut.findByUserId(userId, Pageable.ofSize(5));

        // then
        assertThat(result.getContent())
                .hasSize(1)
                .extracting("userId", String.class)
                .containsOnly(userId);
        assertThat(result.getPageable())
                .hasFieldOrPropertyWithValue("pageSize", 5)
                .hasFieldOrPropertyWithValue("pageNumber", 0);
    }

    @DisplayName("사용자별 테이블 스키마 이름을 조회하면, 테이블 스키마를 반환한다.")
    @Test
    void givenUserIdAndSchemaName_whenSelectingTableSchemas_thenReturnsTableSchema() {
        // given
        var userId = "djkeh";
        var schemaName = "test_schema1";

        // when
        Optional<TableSchema> result = sut.findByUserIdAndSchemaName(userId, schemaName);

        // then
        assertThat(result).get()
                .hasFieldOrPropertyWithValue("userId", userId)
                .hasFieldOrPropertyWithValue("schemaName", schemaName);
    }

    @DisplayName("사용자별 테이블 스키마 이름이 주어지면, 테이블 스키마를 삭제한다.")
    @Test
    void givenUserIdAndSchemaName_whenDeletingTableSchema_thenDelete() {
        // given
        var userId = "djkeh";
        var schemaName = "test_schema1";

        // when
        sut.deleteByUserIdAndSchemaName(userId, schemaName);

        // then
        assertThat(sut.findByUserIdAndSchemaName(userId, schemaName)).isEmpty();
    }

}