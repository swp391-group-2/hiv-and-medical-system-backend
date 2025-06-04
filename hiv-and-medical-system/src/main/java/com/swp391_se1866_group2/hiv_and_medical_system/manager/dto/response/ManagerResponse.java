package com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ManagerResponse {
    String managerId;
    String email;
    String fullName;
    String userStatus;
    String userId;
    String role;
    String managerCode;

}
