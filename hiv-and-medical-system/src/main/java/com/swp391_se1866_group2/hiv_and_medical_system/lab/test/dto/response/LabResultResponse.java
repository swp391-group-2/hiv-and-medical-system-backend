package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response;


import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LabResultResponse {
    int labResultId;
    String resultText;
    Double resultNumeric;
    Double resultNumericCD4;
    Double resultNumericViralLoad;
    String conclusion;
    String note;
    LocalDate resultDate;
    LabSampleResponse labSample;
}
