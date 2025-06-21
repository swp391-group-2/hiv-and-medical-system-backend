package com.swp391_se1866_group2.hiv_and_medical_system.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeResponse {
    String status;
    String message;
    String sessionId;
    String sessionUrl;
}
