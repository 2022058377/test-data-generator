package com.example.testdatagenerator.service;

import com.example.testdatagenerator.dto.TableSchemaDto;
import com.example.testdatagenerator.repository.TableSchemaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TableSchemaService {

    private final TableSchemaRepository tableSchemaRepository;

    @Transactional(readOnly = true)
    public List<TableSchemaDto> loadMySchemas(String userId) {
        return loadMySchemas(userId, Pageable.unpaged()).toList();
    }

    @Transactional(readOnly = true)
    public Page<TableSchemaDto> loadMySchemas(String userId, Pageable pageable) {
        return tableSchemaRepository.findByUserId(userId, pageable)
                .map(TableSchemaDto::from);
    }

    @Transactional(readOnly = true)
    public TableSchemaDto loadMySchema(String userId, String schemaName) {
        return tableSchemaRepository.findByUserIdAndSchemaName(userId, schemaName)
                .map(TableSchemaDto::from)
                .orElseThrow(()-> new EntityNotFoundException(
                        "테이블 스키마가 없습니다. - userId: " + userId + ", schemaName: " + schemaName));
    }

    public void upsertTableSchema(TableSchemaDto dto) {
        tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())
                        .ifPresentOrElse(
                                entity -> tableSchemaRepository.save(dto.updateEntity(entity)),
                                () -> tableSchemaRepository.save(dto.createEntity())
                        );
    }

    public void deleteMySchema(String userId, String schemaName) {
        tableSchemaRepository.deleteByUserIdAndSchemaName(userId, schemaName);
    }
}
