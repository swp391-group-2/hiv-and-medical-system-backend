package com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionItemRepository extends JpaRepository <PrescriptionItem, Integer> {
    boolean existsByName(String name);
    @Query("""
    SELECT new com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.response.PrescriptionItemResponse(
        pi.id, pi.dosage, pi.name, pi.frequency, pi.duration, p.id
    )
    FROM PrescriptionItem pi
    JOIN pi.prescription p
    WHERE p.id = :prescriptionId
""")
    List<PrescriptionItemResponse> findItemByPrescriptionId(@Param("prescriptionId") String prescriptionId);


}
