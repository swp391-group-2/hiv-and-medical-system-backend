package com.swp391_se1866_group2.hiv_and_medical_system.staff.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response.StaffResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.service.StaffService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staffs")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class StaffController {
    StaffService staffService;

    @PostMapping
    public ApiResponse<StaffResponse> createStaff(@RequestBody UserCreationRequest request) {
        return ApiResponse.<StaffResponse>builder()
                .success(true)
                .result(staffService.createStaff(request))
                .build();
    }
}
