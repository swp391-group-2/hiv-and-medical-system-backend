package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DoctorWorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    int id;
    @Column(nullable = false)
    LocalDate workDate;
    String status = "available";
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @OneToMany(mappedBy = "schedule",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleSlot> scheduleSlots = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonManagedReference("doctor-schedules")
    Doctor doctor;
}
