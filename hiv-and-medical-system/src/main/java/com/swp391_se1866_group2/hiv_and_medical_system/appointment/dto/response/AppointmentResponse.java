package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppoimentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.entity.Slot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentResponse {
    int appointmentId;
    PatientResponse patient;
    Integer serviceId;
    String serviceName;
    String serviceType;
    Double price;
    Integer labTestSlotId;
    String doctorName;
    Integer scheduleSlotId;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    String slotDescription;
    Integer prescriptionId;
    Integer labSampleId;
    AppoimentStatus status;
}
