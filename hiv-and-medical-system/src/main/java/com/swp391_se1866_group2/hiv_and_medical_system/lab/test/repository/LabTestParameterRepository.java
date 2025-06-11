package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestParameterRepository extends JpaRepository<LabTestParameter, Integer> {
    LabTestParameter findByLabTestId(Integer labTestId);
}
