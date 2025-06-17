package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response;

import java.time.LocalDate;
import java.util.Set;

public class ScheduleDTOResponse {
    int id;
    LocalDate workDate;
    Set<ScheduleSlotDTOResponse> scheduleSlots;
}
