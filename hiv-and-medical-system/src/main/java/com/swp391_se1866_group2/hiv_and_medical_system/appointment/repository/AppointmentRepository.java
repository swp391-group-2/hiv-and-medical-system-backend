package com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.projection.MaxMinAppointmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findById(int id);
    Optional<List<Appointment>> findByStatus(AppointmentStatus status);
    Optional<List<Appointment>> findByPatient(Patient patient);


    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.createdAt) <= :milestone")
    long countAppointments(@Param("milestone") LocalDate milestone);

    @Query("SELECT COUNT(a) FROM Appointment a ")
    long countTotalAppointments();

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = 'CANCELLED'")
    long countCancelledAppointments();

    @Query("SELECT AVG(avgCount) FROM (SELECT COUNT(a) AS avgCount FROM Appointment a GROUP BY DATE(a.createdAt))")
    Double findAverageAppointmentsPerDay();

    @Query(value = "SELECT DATE(created_at) AS date, COUNT(*) AS count FROM appointment GROUP BY DATE(created_at) ORDER BY count DESC LIMIT 1", nativeQuery = true)
    Optional<MaxMinAppointmentResponse> findMaxAppointmentPerDay();

    @Query(value = "SELECT DATE(created_at) AS date, COUNT(*) AS count FROM appointment GROUP BY DATE(created_at) ORDER BY count ASC LIMIT 1", nativeQuery = true)
    Optional<MaxMinAppointmentResponse> findMinAppointmentPerDay();

    @Query("SELECT COUNT(a) FROM Appointment a JOIN a.service s WHERE s.serviceType = 'CONSULTATION'")
    long countConsultationAppointments();

    @Query("SELECT COUNT(a) FROM Appointment a JOIN a.service s WHERE s.serviceType = 'SCREENING'")
    long countScreeningAppointments();

    @Query("SELECT COUNT(a) FROM Appointment a JOIN a.service s WHERE s.serviceType = 'CONFIRMATORY'")
    long countConfirmatoryAppointments();

    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.appointmentCode = :code WHERE a.id = :id")
    void updateAppointmentCode (@Param("id") int id, @Param("code") String code);

}
