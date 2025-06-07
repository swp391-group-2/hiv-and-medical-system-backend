package com.swp391_se1866_group2.hiv_and_medical_system.medication.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
