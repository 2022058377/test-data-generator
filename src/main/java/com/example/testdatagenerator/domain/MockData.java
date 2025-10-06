package com.example.testdatagenerator.domain;

import com.example.testdatagenerator.domain.constant.MockDataType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mockDataType", "mockDataValue"})
})
@Entity
public class MockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private MockDataType mockDataType;
    @Setter @Column(nullable = false) private String mockDataValue;


    protected MockData(){}

    public MockData(MockDataType mockDataType, String mockDataValue) {
        this.mockDataType = mockDataType;
        this.mockDataValue = mockDataValue;
    }

    public static MockData of(MockDataType mockDataType, String mockDataValue) {
        return new MockData(mockDataType, mockDataValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockData that)) return false;

        if(getId() == null) {
            return Objects.equals(this.getMockDataType(), that.getMockDataType()) &&
                    Objects.equals(this.getMockDataValue(), that.getMockDataValue());
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if(getId() == null) {
            return Objects.hash(getMockDataType(), getMockDataValue());
        }
        return Objects.hash(getId());
    }
}
