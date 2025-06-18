package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaPrescriptionItemResponse {
    int id;
    String dosage;
    String frequency;
    int quantity;
    MedicationResponse medication;
}
