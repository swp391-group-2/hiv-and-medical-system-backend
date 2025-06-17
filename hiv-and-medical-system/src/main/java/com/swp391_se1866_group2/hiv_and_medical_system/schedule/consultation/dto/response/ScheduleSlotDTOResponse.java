package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;

import java.time.LocalDate;

public class ScheduleSlotDTOResponse {
    int id;
    LocalDate date;
    private Integer scheduleId;
    SlotResponse slot;
    String status;
}
