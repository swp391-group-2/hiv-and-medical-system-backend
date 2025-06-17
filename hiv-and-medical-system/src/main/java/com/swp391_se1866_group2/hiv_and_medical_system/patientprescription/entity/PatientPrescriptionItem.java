package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientPrescriptionItem {
    @Id
    int id;
    String dosage;
    String frequency;
    int quantity;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @ManyToOne
    PatientPrescription patientPrescription;
    @ManyToOne
    Medication medication;
}
