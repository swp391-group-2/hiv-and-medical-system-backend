package com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column( columnDefinition = "NVARCHAR(250)")
    String contraindication;

    @Column(nullable = false)
    String sideEffect;

    @Column(columnDefinition = "NVARCHAR(250)", nullable = false)
    String instructions;

    String dosageForm;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "prescription")
    @JsonManagedReference("prescriptionItems-prescription")
    List<com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.PrescriptionItem> prescriptionItems = new ArrayList<>();

}
