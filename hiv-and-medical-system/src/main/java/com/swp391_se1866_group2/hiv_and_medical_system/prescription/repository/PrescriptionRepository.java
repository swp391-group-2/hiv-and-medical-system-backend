package com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    boolean existsByName(String name);
    List<Prescription> findAllByNameContainingIgnoreCase(String prescriptionName);
}
