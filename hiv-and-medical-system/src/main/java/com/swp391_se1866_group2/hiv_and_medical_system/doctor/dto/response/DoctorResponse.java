package com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse {
    String doctorId;
    String userId;
    String email;
    String fullName;
    String userStatus;
    String doctorCode;
    String specialization;
    String licenseNumber;
    String urlImage;
}
