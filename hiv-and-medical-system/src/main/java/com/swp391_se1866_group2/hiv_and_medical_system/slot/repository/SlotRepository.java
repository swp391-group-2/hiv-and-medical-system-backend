package com.swp391_se1866_group2.hiv_and_medical_system.slot.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    Slot findBySlotNumber(int slotNumber);
}
