package com.example.testdatagenerator.domain;

import com.example.testdatagenerator.domain.constant.MockDataType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class SchemaField extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private TableSchema tableSchema;

    @Setter @Column(nullable = false) private String fieldName;
    @Setter @Column(nullable = false) private MockDataType mockDataType;
    @Setter @Column(nullable = false) private Integer fieldOrder;
    @Setter @Column(nullable = false) private Integer blankPercent;

    @Setter private String typeOptionJson;
    @Setter private String forceValue;

    protected SchemaField() {}

    public SchemaField(String fieldName,
                       MockDataType mockDataType,
                       Integer fieldOrder,
                       Integer blankPercent,
                       String typeOptionJson,
                       String forceValue) {
        this.fieldName = fieldName;
        this.mockDataType = mockDataType;
        this.fieldOrder = fieldOrder;
        this.blankPercent = blankPercent;
        this.typeOptionJson = typeOptionJson;
        this.forceValue = forceValue;
    }

    public static SchemaField of(String fieldName,
                                 MockDataType mockDataType,
                                 Integer fieldOrder,
                                 Integer blankPercent,
                                 String typeOptionJson,
                                 String forceValue) {
        return new SchemaField(fieldName, mockDataType, fieldOrder, blankPercent, typeOptionJson, forceValue);
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof SchemaField that)) return false;

        if(getId() == null) {
            return Objects.equals(this.getFieldName(), that.getFieldName()) &&
                    Objects.equals(this.getMockDataType(), that.getMockDataType()) &&
                    Objects.equals(this.getFieldOrder(), that.getFieldOrder()) &&
                    Objects.equals(this.getBlankPercent(), that.getBlankPercent()) &&
                    Objects.equals(this.getTypeOptionJson(), that.getTypeOptionJson()) &&
                    Objects.equals(this.getForceValue(), that.getForceValue());
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if(getId() == null) {
            return Objects.hash(getFieldName(), getMockDataType(), getFieldOrder(), getBlankPercent(), getTypeOptionJson(), getForceValue());

        }
        return Objects.hash(getId());
    }
}
