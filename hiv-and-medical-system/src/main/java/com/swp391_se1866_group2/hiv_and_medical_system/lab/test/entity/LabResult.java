package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity;


import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Double resultNumericCD4;
    Double resultNumericViralLoad;
    String resultText;
    @CreationTimestamp
    @Column(nullable = false)
    LocalDate resultDate;

    @Column(nullable = false)
    String conclusion;

    @Column(nullable = false, columnDefinition = "NVARCHAR(200)")
    String note;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_parameter_id", nullable = false)
    LabTestParameter labTestParameter;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_sample_id", nullable = false)
    LabSample labSample;



}
