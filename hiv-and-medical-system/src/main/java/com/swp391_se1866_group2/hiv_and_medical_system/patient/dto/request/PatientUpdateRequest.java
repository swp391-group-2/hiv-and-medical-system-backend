package com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientUpdateRequest {
    String fullName;
    LocalDate dob;
    String gender;
    String address;
    String phoneNumber;
    String identificationCard;
    String healthInsurance;
    String occupation;
}
