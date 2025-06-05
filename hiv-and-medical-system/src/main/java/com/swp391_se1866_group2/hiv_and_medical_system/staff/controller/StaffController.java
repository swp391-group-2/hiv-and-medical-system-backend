package com.swp391_se1866_group2.hiv_and_medical_system.staff.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response.StaffResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.service.StaffService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class StaffController {
    StaffService staffService;

    @PostMapping
    public ApiResponse<StaffResponse> createStaff(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<StaffResponse>builder()
                .success(true)
                .result(staffService.createStaff(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<StaffResponse>> getAllStaffs() {
        return ApiResponse.<List<StaffResponse>>builder()
                .result(staffService.getAllStaffs())
                .success(true)
                .build();
    }

    @GetMapping("/{staffId}")
    public ApiResponse<StaffResponse> getStaff(@PathVariable String staffId) {
        return ApiResponse.<StaffResponse>builder()
                .result(staffService.getStaff(staffId))
                .success(true)
                .build();
    }
}
