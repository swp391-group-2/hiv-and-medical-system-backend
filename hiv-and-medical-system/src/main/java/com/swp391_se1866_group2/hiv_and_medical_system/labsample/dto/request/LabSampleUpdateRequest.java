package com.swp391_se1866_group2.hiv_and_medical_system.labsample.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabSampleUpdateRequest {
    String sampleCode;
    String sampleType;
}
