package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabSampleStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    String sampleCode;
    String sampleType;
    String sampleCodeCD4;
    String sampleTypeCD4;
    String sampleCodeVirus;
    String sampleTypeVirus;
    @OneToOne(
            mappedBy = "labSample",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            orphanRemoval = true
    )
    LabResult labResults;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    Appointment appointment;
    @CreationTimestamp
    LocalDateTime collectedAt;
    @Enumerated(EnumType.STRING)
    LabSampleStatus status = LabSampleStatus.PENDING;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
