package com.example.testdatagenerator.repository;

import com.example.testdatagenerator.domain.MockData;
import com.example.testdatagenerator.domain.constant.MockDataType;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockDataRepository extends JpaRepository<MockData, Long> {

    @Cacheable("mockData")
    List<MockData> findByMockDataType(MockDataType mockDataType);
}
