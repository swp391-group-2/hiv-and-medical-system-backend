package com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppoimentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findById(int id);
    List<Appointment> findByStatus(AppoimentStatus status);

}
