package com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientResponse {
    String patientId;
    String userId;
    String email;
    String fullName;
    String createdAt;
    String userStatus;
    String patientCode;
    LocalDate dob;
    String gender;
    String address;
    String phoneNumber;
    String identificationCard;
    String healthInsurance;
    String occupation;
    LocalDateTime updatedAt;
}
