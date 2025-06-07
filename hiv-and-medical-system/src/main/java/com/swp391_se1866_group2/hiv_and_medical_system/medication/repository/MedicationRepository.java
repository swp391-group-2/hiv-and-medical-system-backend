package com.swp391_se1866_group2.hiv_and_medical_system.medication.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, String> {
    boolean existsByNameAndStrength(String name, String strength);

    boolean existsByNameAndStrengthAndIdNot(String name, String strength, String id);
}
