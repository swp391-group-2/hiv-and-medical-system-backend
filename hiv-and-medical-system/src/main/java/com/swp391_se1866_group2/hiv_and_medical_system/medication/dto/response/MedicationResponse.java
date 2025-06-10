package com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response;

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
public class MedicationResponse {
    int medicationId;
    String name;
    String description;
    String dosageForm;
    String strength;
}
