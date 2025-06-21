package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
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
    String appointmentCode;
    AppointmentStatus status;
    PatientResponse patient;
    Integer serviceId;
    String serviceName;
    String serviceType;
    Long price;
    String note;
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
    PaPrescriptionResponse patientPrescription;
}
