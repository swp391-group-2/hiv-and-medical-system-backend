package com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PrescriptionUpdateRequest {
    String name;
    String contraindication;
    String sideEffect;
    String dosageForm;
    String instructions;
    List<PrescriptionItemCreationRequest> prescriptionItems;
}
