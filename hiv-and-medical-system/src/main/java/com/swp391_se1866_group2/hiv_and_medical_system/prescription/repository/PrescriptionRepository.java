package com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    boolean existsByName(String name);
    List<Prescription> findAllByNameContainingIgnoreCase(String prescriptionName);
}
