package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LabTestSlotRepository extends JpaRepository<LabTestSlot, Integer> {
    boolean existsLabTestSlotByDateAndSlot(LocalDate date, Slot slot);

    List<LabTestSlot> findAllByDate(LocalDate date);

    @Query("SELECT l FROM LabTestSlot l WHERE l.id = :labTestSlotId")
    LabTestSlot findByLabTestSlotId(@Param("labTestSlotId") int labTestSlotId);

    @Query("SELECT l FROM LabTestSlot l WHERE l.date <= :date AND l.status != 'EXPIRED' ")
    List<LabTestSlot> findAllLabTestSlotExpiredByDate(@Param("date") LocalDate date);
}
