package com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleCreationRequest {
    LocalDate workDate;
    Set<Integer> slotId;
}
