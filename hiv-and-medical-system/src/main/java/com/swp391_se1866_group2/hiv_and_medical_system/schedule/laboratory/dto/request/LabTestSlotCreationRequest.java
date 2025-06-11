package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabTestSlotCreationRequest {
    LocalDate date;
    List<Integer> slots;
}
