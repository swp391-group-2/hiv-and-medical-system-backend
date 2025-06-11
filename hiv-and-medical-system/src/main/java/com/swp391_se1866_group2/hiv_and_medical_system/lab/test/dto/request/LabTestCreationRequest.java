package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabTestCreationRequest {
    String name;
    String description;
    TestType testType;
    String method;
    LabTestParameterCreationRequest labTestParameter;
    int serviceId;
}
