package com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    boolean existsByName(String name);
}
