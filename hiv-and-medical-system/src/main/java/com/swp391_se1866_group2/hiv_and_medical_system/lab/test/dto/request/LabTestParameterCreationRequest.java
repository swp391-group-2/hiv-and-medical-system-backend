package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ParameterType;
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
public class LabTestParameterCreationRequest {
    String parameterName;
    String unitCD4;
    String unitViralLoad;
    String normalRangeCD4;
    String normalRangeStringViralLoad;
    String description;
    ParameterType parameterType;
}
