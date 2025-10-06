package com.example.testdatagenerator.repository;

import com.example.testdatagenerator.domain.SchemaField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaFieldRepository extends JpaRepository<SchemaField, Long> {
}
