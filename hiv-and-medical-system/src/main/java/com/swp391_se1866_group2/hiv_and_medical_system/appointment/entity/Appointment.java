package com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne(fetch = FetchType.LAZY)
    Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    LabTestSlot labTestSlot;
    @ManyToOne(fetch = FetchType.LAZY)
    ServiceEntity service;
    @ManyToOne(fetch = FetchType.LAZY)
    Prescription prescription;
    @ManyToOne(fetch = FetchType.LAZY)
    ScheduleSlot scheduleSlot;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "lab_sample_id")
    LabSample labSample;
    @Enumerated(EnumType.STRING)
    AppointmentStatus status;
    String note;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
