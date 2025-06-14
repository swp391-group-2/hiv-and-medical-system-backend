package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ResultStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabResultResponse {
    int labResultId;
    String resultText;
    Double resultNumericCD4;
    Double resultNumericViralLoad;
    String conclusion;
    String note;
    ResultStatus resultStatus;
    LocalDate resultDate;
    LabSampleResponse labSample;
    LabTestParameterResponse labTestParameter;
}
