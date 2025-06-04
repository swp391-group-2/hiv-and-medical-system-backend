package com.swp391_se1866_group2.hiv_and_medical_system.patient.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

}
