package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabTestSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    LocalDate date;
    @ManyToOne
    Slot slot;
    int bookedCount;
    int maxCount = 5;
    @Enumerated(EnumType.STRING)
    LabTestStatus status;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
