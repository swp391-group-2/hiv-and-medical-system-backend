package com.swp391_se1866_group2.hiv_and_medical_system.patient.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query(""" 
            SELECT new com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse (p.id, u.id, u.email, u.fullName, u.status, u.code, p.dob, p.gender, p.address, p.phoneNumber, p.identificationCard, p.healthInsurance, p.occupation) FROM Patient p JOIN p.user u WHERE u.email = :email""")
    Optional<PatientResponse> findPatientByUserEmail(@Param("email") String email);
}
