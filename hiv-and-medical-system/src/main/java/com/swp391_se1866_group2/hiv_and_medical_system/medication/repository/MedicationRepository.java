package com.swp391_se1866_group2.hiv_and_medical_system.medication.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    boolean existsByNameAndStrength(String name, String strength);

    boolean existsByNameAndStrengthAndIdNot(String name, String strength, int id);

//    Medication findByNameAndStrength(String name, String strength);
}
