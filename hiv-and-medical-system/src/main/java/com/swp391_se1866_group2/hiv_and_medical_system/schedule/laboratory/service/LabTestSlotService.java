package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ScheduleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequestBulk;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response.LabTestSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.repository.LabTestSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.service.SlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabTestSlotService {
    LabTestSlotRepository labTestSlotRepository;
    SlotService slotService;
    ScheduleMapper scheduleMapper;


//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<LabTestSlotResponse> createLabTestSlotBulk (LabTestSlotCreationRequest request) {
        List<Slot> slots = request.getSlots().stream()
                .map(slotId -> slotService.getSlotById(slotId) )
                .collect(Collectors.toList());
        List<LabTestSlotResponse> labTestSlotResponses = new ArrayList<>();
        slots.forEach(slot -> {
            labTestSlotResponses.add(createLabTestSlot(slot, request.getDate()));
        });
        return labTestSlotResponses;
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<List<LabTestSlotResponse>> createLabTestSlotBulkBetweenDate (LabTestSlotCreationRequestBulk request, LocalDate startDate, LocalDate endDate) {
        List<List<LabTestSlotResponse>> labTestSlotResponses = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LabTestSlotCreationRequest labTestSlotCreation = new LabTestSlotCreationRequest();
            labTestSlotCreation.setDate(date);
            labTestSlotCreation.setSlots(request.getSlots());
            labTestSlotResponses.add(createLabTestSlotBulk(labTestSlotCreation));
        }
        return labTestSlotResponses;
    }

    public List<LabTestSlotResponse> getAllLabTestSlots () {
        return labTestSlotRepository.findAll().stream()
                .map(labTestSlot -> scheduleMapper.toLabTestSlotResponse(labTestSlot))
                .collect(Collectors.toList());
    }

    public List<LabTestSlotResponse> getAllLabTestSlotByDate(LocalDate date){
        return labTestSlotRepository.findAllByDate(date).stream()
                .map(labTestSlot -> scheduleMapper.toLabTestSlotResponse(labTestSlot))
                .collect(Collectors.toList());
    }

    public LabTestSlotResponse getLabTestResponseSlotById(int id){
        return scheduleMapper.toLabTestSlotResponse(labTestSlotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LAB_TEST_SLOT_NOT_EXISTED)));
    }

    public LabTestSlotResponse createLabTestSlot(Slot slot, LocalDate date) {
        if(labTestSlotRepository.existsLabTestSlotByDateAndSlot(date, slot)){
            throw new AppException(ErrorCode.LAB_TEST_SLOT_EXISTED);
        }
        LabTestSlot labTestSlot = new LabTestSlot();
        labTestSlot.setSlot(slot);
        labTestSlot.setDate(date);
        labTestSlot.setStatus(LabTestStatus.AVAILABLE);
        return scheduleMapper.toLabTestSlotResponse(labTestSlotRepository.save(labTestSlot));
    }

    public LabTestSlot getLabTestSlotById(int id){
        return labTestSlotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LAB_TEST_SLOT_NOT_EXISTED));
    }

    public LabTestSlot getLabTestSlotBySlotId(int id){
        return labTestSlotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LAB_TEST_SLOT_NOT_EXISTED));
    }



}
