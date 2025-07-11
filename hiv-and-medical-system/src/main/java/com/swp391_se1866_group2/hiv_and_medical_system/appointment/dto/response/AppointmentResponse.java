package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentResponse {
    int appointmentId;
    String appointmentCode;
    PatientResponse patient;
    Integer serviceId;
    String serviceName;
    String serviceType;
    Long price;
    Integer labTestSlotId;
    String doctorName;
    Integer scheduleSlotId;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String slotDescription;
    Integer patientPrescriptionId;
    Integer labSampleId;
    AppointmentStatus status;
}
