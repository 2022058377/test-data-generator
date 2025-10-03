package com.example.testdatagenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TableSchema {

    private String schemaName;
    private String userId;
    private LocalDateTime exportedAt;

}
