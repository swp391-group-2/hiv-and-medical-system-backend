package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointment;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, Integer> {
    ScheduleSlot findScheduleSlotById(int id);

    @Query("SELECT sch FROM ScheduleSlot sch JOIN sch.slot sl WHERE sl.id = :slotId AND sch.status = 'AVAILABLE' AND sch.schedule.workDate = :workDate ")
    Page<ScheduleSlot> chooseDoctorBySlotId(@Param("slotId") int slotId, @Param("workDate")LocalDate workDate, Pageable pageable);

    @Query("SELECT ss from ScheduleSlot ss JOIN ss.schedule s JOIN ss.slot sl WHERE s.id = :doctorWorkScheduleId AND sl.id = :slotId ")
    ScheduleSlot findScheduleSlotBySlotIdAndDoctorWorkScheduleId(@Param("slotId") int slotId, @Param("doctorWorkScheduleId") int doctorWorkScheduleId);

    @Query("SELECT ss FROM ScheduleSlot ss JOIN ss.schedule sch WHERE ss.status = :status AND sch.workDate <= :date")
    List<ScheduleSlot> findAllByStatusAndDateBefore( @Param("status")ScheduleSlotStatus status, @Param("date") LocalDate date);

    @Query("SELECT ss FROM Appointment a JOIN a.scheduleSlot ss JOIN ss.schedule sch WHERE ss.status = :status AND a.status = :appointmentStatus AND sch.workDate <= :date")
    List<ScheduleSlot> findAllByStatusAndAppoiStatusAndDateBefore(@Param("status")ScheduleSlotStatus status, @Param("appointmentStatus")AppointmentStatus appointmentStatus, @Param("date") LocalDate date);

}
