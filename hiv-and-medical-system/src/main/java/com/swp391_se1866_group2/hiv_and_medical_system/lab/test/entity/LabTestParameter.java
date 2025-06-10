package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ParameterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabTestParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    String parameterName;

    ParameterType parameterType;

    String unit;

    String normalRange;

    String description;

    @CreationTimestamp
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_id", nullable = false)
    @JsonBackReference
    LabTest labTest;
}
