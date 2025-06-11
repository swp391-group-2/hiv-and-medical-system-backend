package com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Integer> {



}
