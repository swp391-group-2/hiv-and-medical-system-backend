package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ScheduleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleSlotService {
    ScheduleSlotRepository scheduleSlotRepository;
    ScheduleMapper scheduleMapper;

    public ScheduleSlot createScheduleSlot (ScheduleSlot scheduleSlot) {
        return scheduleSlotRepository.save(scheduleSlot);
    }

    public ScheduleSlotResponse updateScheduleSlotStatus (int id) {
        ScheduleSlot scheduleSlot = scheduleSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.SCHEDULE_NOT_EXISTED.getMessage()));
        scheduleSlot.setStatus(ScheduleSlotStatus.UNAVAILABLE.name());
        return scheduleMapper.toScheduleSlotResponse(scheduleSlotRepository.save(scheduleSlot)) ;
    }

    public ScheduleSlot getScheduleSlotById (int scheduleSlotId) {
        return scheduleSlotRepository.findById(scheduleSlotId).orElseThrow(() -> new RuntimeException(ErrorCode.SCHEDULE_SLOT_NOT_EXISTED.getMessage()));
    }


}
