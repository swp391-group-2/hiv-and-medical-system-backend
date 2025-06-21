package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentCreationResponse {
    int appointmentId;
    String appointmentCode;
    String patientId;
    String serviceName;
    String serviceType;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String doctorName;
    int labSampleId;
    AppointmentStatus status;
}
