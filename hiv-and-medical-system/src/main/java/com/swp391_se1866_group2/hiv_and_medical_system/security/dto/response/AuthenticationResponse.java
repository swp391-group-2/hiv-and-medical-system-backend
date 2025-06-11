package com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    Object user;
    String accessToken;
    String refreshToken;
    boolean authenticated;
}
