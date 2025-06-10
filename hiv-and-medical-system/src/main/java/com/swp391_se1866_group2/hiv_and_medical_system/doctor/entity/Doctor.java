package com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String specialization;
    String licenseNumber;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference("user-doctor")
    User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @JsonBackReference("doctor-schedules")
    Set<DoctorWorkSchedule> doctorWorkSchedules;
}
