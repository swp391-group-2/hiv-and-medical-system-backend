package com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PrescriptionResponse {
    String prescriptionId;
    String name;
    String dosage;
    String contraindication;
    String sideEffect;
    String instructions;
    String date;

}
