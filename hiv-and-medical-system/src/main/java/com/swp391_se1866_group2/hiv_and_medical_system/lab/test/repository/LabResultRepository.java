package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, Integer> {
    LabResult findByLabSampleId(int id);
    Optional<LabResult> findLabResultByLabSampleId(int id);
}
