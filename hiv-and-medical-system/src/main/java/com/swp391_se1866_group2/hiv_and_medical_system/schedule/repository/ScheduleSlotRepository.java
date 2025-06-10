package com.swp391_se1866_group2.hiv_and_medical_system.schedule.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.ScheduleSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, Integer> {
}
