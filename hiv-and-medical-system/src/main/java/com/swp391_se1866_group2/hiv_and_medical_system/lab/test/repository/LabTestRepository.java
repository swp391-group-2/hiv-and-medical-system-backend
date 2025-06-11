package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Integer> {
    List<LabTest> findAllByNameContainingIgnoreCase(String labTestName);
    LabTest findByServiceServiceType(ServiceType serviceServiceType);

}
