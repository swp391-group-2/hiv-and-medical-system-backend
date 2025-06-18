package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaPrescriptionResponse {
    int id;
    int duration;
    String note;
    LocalDateTime createdAt;
    int prescriptionDefaultId;
    String prescriptionDefaultName;
    List<PaPrescriptionItemResponse> patientPrescriptionItems;
}
