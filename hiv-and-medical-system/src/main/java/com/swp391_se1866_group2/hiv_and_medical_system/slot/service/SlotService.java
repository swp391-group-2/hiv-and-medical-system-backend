package com.swp391_se1866_group2.hiv_and_medical_system.slot.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.SlotMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request.SlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.repository.SlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SlotService {
    SlotRepository slotRepository;
    SlotMapper slotMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SlotResponse createSlot (SlotCreationRequest request){
        if(slotRepository.findBySlotNumber(request.getSlotNumber()) != null){
            throw new AppException(ErrorCode.SLOT_EXISTED);
        }
        return slotMapper.toSlotResponse(slotRepository.save(slotMapper.toSlot(request)));
    }

    

}
