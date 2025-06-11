package com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppoimentRepository extends JpaRepository<Appointment, Integer> {
}
