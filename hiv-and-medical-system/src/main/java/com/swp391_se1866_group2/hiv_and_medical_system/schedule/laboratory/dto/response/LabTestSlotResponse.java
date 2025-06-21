package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabTestSlotResponse {
    int id;
    LocalDate date;
    Slot slot;
    int bookedCount;
    int maxCount;
    LabTestStatus status;
}
