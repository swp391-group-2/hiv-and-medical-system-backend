package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    String doctorId;
    String userId;
    String email;
    String fullName;
    String userStatus;
    String doctorCode;
    String specialization;
    String licenseNumber;
}
