package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request.SlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import org.mapstruct.Mapper;

@Mapper
public interface SlotMapper {
    Slot toSlot(SlotCreationRequest request);
    SlotResponse toSlotResponse(Slot slot);
}
