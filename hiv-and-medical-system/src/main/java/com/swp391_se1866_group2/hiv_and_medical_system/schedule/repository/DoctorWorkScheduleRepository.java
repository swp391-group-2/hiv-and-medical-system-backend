package com.swp391_se1866_group2.hiv_and_medical_system.schedule.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.DoctorWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorWorkScheduleRepository extends JpaRepository<DoctorWorkSchedule, Integer> {
}
