package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int duration;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Appointment appointment;
    @CreationTimestamp
    @Column(updatable = false)
    String note;
    @ManyToOne(fetch = FetchType.LAZY)
    Prescription prescription;
    @OneToMany
    @JsonManagedReference("patientPrescriptionItems-patientPrescription")
    List<PatientPrescriptionItem> patientPrescriptionItems;
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
