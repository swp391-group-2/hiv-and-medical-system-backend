package com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleSlotResponse {
    int id;
    SlotResponse slot;
    String status;
}
