package com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotCreationRequest {
    int slotNumber;
    LocalTime startTime;
    LocalTime endTime;
    String description;
}
