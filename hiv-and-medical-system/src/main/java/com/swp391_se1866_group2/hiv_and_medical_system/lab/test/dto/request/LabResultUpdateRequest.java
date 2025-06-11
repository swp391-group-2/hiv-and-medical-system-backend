package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request;


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
public class LabResultUpdateRequest {
    String resultText;
    Double resultNumericCD4;
    Double resultNumericViralLoad;
    String conclusion;
    String note;
}
