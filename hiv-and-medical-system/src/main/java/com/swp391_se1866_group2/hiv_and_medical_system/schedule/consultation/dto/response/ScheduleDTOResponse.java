package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleDTOResponse {
    int id;
    LocalDate workDate;
    Set<ScheduleSlotDateResponse> scheduleSlots;
}
