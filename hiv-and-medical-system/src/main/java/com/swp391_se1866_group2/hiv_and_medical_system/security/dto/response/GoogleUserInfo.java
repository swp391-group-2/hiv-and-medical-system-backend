package com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response;

import lombok.Data;

@Data
public class GoogleUserInfo {
    String sub;
    String name;
    String email;
    boolean email_verified;
    String picture;
}
