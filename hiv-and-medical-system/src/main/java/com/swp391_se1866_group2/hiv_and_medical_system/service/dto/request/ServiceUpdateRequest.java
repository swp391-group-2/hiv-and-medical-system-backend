package com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceUpdateRequest {
    String name;
    double price;
}
