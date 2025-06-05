package com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)

public class StaffResponse {
    String staffId;
    String email;
    String fullName;
    String userStatus;
    String userId;
    String role;
    String staffCode;
}
