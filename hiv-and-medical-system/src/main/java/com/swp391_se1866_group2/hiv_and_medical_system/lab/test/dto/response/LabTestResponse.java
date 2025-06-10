package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TestType;
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
public class LabTestResponse {
    int labTestId;
    String name;
    String description;
    TestType testType;
    String method;
    LabTestParameterResponse labTestParameter;
}
