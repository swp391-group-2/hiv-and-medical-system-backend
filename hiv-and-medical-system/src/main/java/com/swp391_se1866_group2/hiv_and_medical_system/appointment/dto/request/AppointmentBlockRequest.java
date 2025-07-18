package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentBlockRequest {
    int id;
    boolean continuity;
}
