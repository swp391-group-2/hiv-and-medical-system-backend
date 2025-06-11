package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response;

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
public class LabTestParameterResponse {
    int labTestParameterId;
    String parameterName;
    ParameterType parameterType;
    String unit;
    String normalRangeCD4;
     String normalRangeStringViralLoad;
    String description;
}
