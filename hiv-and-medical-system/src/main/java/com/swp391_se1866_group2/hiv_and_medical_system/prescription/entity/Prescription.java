package com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
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

public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String dosage;
    String contraindication;
    String sideEffect;
    String instructions;
    LocalDate date;

    @CreationTimestamp
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;


}
