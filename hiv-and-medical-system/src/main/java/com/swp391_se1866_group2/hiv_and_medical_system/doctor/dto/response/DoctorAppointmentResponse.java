package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response;

import lombok.Data;

@Data
public class DoctorAppointmentResponse {
    DoctorResponse doctor;
    long totalAppointment;
}
