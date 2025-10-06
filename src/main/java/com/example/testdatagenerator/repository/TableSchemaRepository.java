package com.example.testdatagenerator.repository;

import com.example.testdatagenerator.domain.TableSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableSchemaRepository extends JpaRepository<TableSchema, Long> {
}
