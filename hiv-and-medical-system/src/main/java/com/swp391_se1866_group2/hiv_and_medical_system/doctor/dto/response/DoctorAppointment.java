package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAppointment {
    Doctor doctor;
    long totalAppointment;
}
