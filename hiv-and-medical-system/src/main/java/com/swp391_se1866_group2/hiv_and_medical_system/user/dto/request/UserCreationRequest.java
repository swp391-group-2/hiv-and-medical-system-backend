package com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request;

import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.RoleRequest;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String phoneNumber;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    RoleRequest role;
}
