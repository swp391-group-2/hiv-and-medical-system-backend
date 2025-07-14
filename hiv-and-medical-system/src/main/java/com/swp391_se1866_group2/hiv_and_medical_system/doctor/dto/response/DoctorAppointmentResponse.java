package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorAppointmentResponse {
    String doctorId;
    String userId;
    String email;
    String fullName;
    String userStatus;
    String doctorCode;
    String specialization;
    String licenseNumber;
    String urlImage;
    long totalAppointment;
}
