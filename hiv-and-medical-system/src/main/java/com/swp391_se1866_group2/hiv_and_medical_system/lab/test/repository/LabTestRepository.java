package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabTestRepository extends JpaRepository<LabTest, Integer> {
    List<LabTest> findAllByNameContainingIgnoreCase(String labTestName);

}
