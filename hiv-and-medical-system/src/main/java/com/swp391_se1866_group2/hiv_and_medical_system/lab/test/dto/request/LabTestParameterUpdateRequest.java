package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request;


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
public class LabTestParameterUpdateRequest {
    String unit;
    String normalRange;
    String description;
}
