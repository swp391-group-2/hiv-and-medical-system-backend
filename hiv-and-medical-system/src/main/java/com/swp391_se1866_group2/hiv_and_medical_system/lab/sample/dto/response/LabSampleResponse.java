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
    String sampleCodeCD4;
    String sampleTypeCD4;
    String sampleCodeVirus;
    String sampleTypeVirus;
    LabSampleResponse results;
    LocalDateTime collectedAt;
    String status;
}
