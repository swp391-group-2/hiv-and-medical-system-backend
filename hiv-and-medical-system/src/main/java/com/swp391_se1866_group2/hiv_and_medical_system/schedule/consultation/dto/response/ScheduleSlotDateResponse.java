package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleSlotDateResponse {
    int id;
    LocalDate date;
    Integer scheduleId;
    SlotResponse slot;
    String status;
}
