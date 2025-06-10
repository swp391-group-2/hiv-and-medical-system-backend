package com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("prescriptionItems-prescription")
    Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("prescriptionItems-medication")
    Medication medication;

    String dosage;
    String frequency;
    String duration;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;



}
