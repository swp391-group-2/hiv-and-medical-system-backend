package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    String name;

    @Column(columnDefinition = "NVARCHAR(200)", nullable = false)
    String description;

    @Column(nullable = false)
    TestType testType;

    @Column(nullable = false)
    String method;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(mappedBy = "labTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    LabTestParameter labTestParameter;

}
