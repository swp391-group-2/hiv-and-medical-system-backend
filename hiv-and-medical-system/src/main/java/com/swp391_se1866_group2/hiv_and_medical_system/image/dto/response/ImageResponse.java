package com.swp391_se1866_group2.hiv_and_medical_system.image.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    int id;
    String url;
    boolean isActive;
    String doctorId;
}
