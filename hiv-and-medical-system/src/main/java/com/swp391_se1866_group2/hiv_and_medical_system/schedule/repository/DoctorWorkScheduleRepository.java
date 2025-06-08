package com.swp391_se1866_group2.hiv_and_medical_system.schedule.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.DoctorWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorWorkScheduleRepository extends JpaRepository<DoctorWorkSchedule, Integer> {

    List<DoctorWorkSchedule> findAllByWorkDateAndDoctorId(LocalDate workDate, String doctorId);
    boolean existsByWorkDateAndDoctorId(LocalDate workDate, String doctorId);
}
