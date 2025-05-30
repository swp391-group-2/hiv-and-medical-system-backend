package com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.RoleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String phoneNumber;
    String name;
    RoleResponse roles;
}
