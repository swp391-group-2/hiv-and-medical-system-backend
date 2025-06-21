package com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.projection.MaxMinAppointmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findById(int id);
    Optional<List<Appointment>> findByStatus(AppointmentStatus status);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE DATE(a.createdAt) BETWEEN :startDate AND :endDate")
    long countAppointments(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

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

}
