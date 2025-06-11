package com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceResponse {
    int id;
    String name;
    double price;
    ServiceType serviceType;
}
