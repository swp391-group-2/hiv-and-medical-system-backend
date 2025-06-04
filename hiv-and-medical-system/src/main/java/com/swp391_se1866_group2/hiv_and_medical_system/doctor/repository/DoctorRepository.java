package com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
