package com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)

public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false, columnDefinition = "NVARCHAR(200)")
    String contraindication;

    @Column(nullable = false)
    String sideEffect;

    @Column(columnDefinition = "NVARCHAR(200)", nullable = false)
    String instructions;

    LocalDate prescriptionDate;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "prescription")
    @JsonManagedReference("prescriptionItems-prescription")
    List<com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.PrescriptionItem> prescriptionItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

}
