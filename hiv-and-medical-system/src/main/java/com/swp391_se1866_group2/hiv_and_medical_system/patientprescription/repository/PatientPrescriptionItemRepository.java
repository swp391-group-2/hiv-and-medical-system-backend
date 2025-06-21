package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientPrescriptionItemRepository extends JpaRepository<PatientPrescriptionItem, Integer> {
}
