package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorCreationRequest {
    String email;
    String password;
    String fullName;
    String specialization;
    String licenseNumber;
}
