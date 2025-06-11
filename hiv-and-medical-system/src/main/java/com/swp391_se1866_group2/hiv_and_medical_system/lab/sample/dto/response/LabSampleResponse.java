package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabSampleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabSampleResponse {
    int id;
    String sampleCode;
    String sampleType;
    LocalDateTime collectedAt;
    String status;
}
