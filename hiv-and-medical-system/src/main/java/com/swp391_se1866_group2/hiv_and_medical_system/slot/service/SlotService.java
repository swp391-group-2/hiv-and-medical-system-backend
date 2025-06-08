package com.swp391_se1866_group2.hiv_and_medical_system.slot.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.SlotMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request.SlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request.SlotUpDateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.repository.SlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<SlotResponse> getAllSlots(){
        return slotRepository.findAll().stream()
                .map(slot -> slotMapper.toSlotResponse(slot))
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SlotResponse getSlotResponseBy (int id){
        return slotMapper.toSlotResponse(slotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SLOT_NOT_EXISTED)));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public SlotResponse updateSlot (int id, SlotUpDateRequest request){
        Slot slot = slotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SLOT_NOT_EXISTED));
        slotMapper.updateSlot(request, slot);
        return slotMapper.toSlotResponse(slotRepository.save(slot));
    }

    public Slot getSlotById (int id){
        return slotRepository.findById(id).orElseThrow(() ->  new AppException(ErrorCode.SLOT_NOT_EXISTED));
    }




}
