package com.swp391_se1866_group2.hiv_and_medical_system.schedule.service;

import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.repository.ScheduleSlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleSlotService {
    ScheduleSlotRepository scheduleSlotRepository;

    public ScheduleSlot createScheduleSlot (ScheduleSlot scheduleSlot) {
        return scheduleSlotRepository.save(scheduleSlot);
    }


}
