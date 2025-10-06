package com.example.testdatagenerator.repository;

import com.example.testdatagenerator.domain.MockData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockDataRepository extends JpaRepository<MockData, Long> {
}
