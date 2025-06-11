package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("scheduleSlot-schedules")
    DoctorWorkSchedule schedule;
    @ManyToOne(fetch = FetchType.LAZY)
    Slot slot;
    String status = ScheduleSlotStatus.AVAILABLE.name();
}
