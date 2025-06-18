package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaPrescriptionItemCreation {
    String dosage;
    String frequency;
    int quantity;
    int medicationId;
}
