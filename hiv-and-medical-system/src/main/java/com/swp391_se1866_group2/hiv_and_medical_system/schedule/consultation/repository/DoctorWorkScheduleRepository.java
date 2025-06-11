package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.DoctorWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorWorkScheduleRepository extends JpaRepository<DoctorWorkSchedule, Integer> {

    List<DoctorWorkSchedule> findAllByWorkDateAndDoctorId(LocalDate workDate, String doctorId);
    boolean existsByWorkDateAndDoctorId(LocalDate workDate, String doctorId);
    List<DoctorWorkSchedule> findAllByDoctorId(String doctorId);
    List<DoctorWorkSchedule> findAllByWorkDateBetweenAndDoctorId(LocalDate start, LocalDate end, String doctorId);
    Optional<DoctorWorkSchedule> findByWorkDate(LocalDate workDate);
}
