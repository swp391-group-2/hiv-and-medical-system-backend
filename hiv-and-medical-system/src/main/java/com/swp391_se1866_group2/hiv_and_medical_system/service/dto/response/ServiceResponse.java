package com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
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
    Long price;
    ServiceType serviceType;
    String imageUrl;
}
