package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppoimentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentLabSampleResponse {
    int appointmentId;
    AppoimentStatus status;
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
    Integer labSampleId;
    LabSampleResponse labSample;
    LabResultResponse labResult;
    PrescriptionResponse prescription;
}
