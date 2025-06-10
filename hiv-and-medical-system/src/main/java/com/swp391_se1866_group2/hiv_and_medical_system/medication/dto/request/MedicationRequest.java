package com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request;

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
public class MedicationRequest {
    String name;
    String strength;
    String dosageForm;
    String description;
}
