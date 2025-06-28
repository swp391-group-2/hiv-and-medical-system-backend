package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointment;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, Integer> {
    ScheduleSlot findScheduleSlotById(int id);

    @Query("SELECT new com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointment(d.doctor, SUM(CASE WHEN s.status = 'UNAVAILABLE' THEN 1 ELSE 0 END))  FROM ScheduleSlot s RIGHT JOIN s.schedule d GROUP BY d ORDER BY SUM(CASE WHEN s.status = 'UNAVAILABLE' THEN 1 ELSE 0 END) DESC ")
    Slice<DoctorAppointment> getTopDoctorByAppointmentCount(Pageable pageable);

    @Query("SELECT sch FROM ScheduleSlot sch JOIN sch.slot sl WHERE sl.id = :slotId AND sch.status = 'AVAILABLE' ")
    Page<ScheduleSlot> chooseDoctorBySlotId(@Param("slotId") int slotId, Pageable pageable);

}
