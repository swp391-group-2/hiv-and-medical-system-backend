package com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCreationRequest {
    String name;
    double price;
    ServiceType serviceType;
}
