package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabSampleRepository extends JpaRepository<LabSample, Integer> {
   Optional<LabSample> findBySampleCode(String sampleCode);
   boolean existsBySampleCode(String sampleCode);
}
