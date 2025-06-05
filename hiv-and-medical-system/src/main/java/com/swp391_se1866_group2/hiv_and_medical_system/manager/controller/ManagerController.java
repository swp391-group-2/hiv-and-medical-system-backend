package com.swp391_se1866_group2.hiv_and_medical_system.manager.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response.ManagerResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.service.ManagerService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class ManagerController {
    ManagerService managerService;

    @PostMapping
    public ApiResponse<ManagerResponse> createManager(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<ManagerResponse>builder()
                .success(true)
                .result(managerService.createManager(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ManagerResponse>> getAllManagers() {
        return ApiResponse.<List<ManagerResponse>>builder()
                .result(managerService.getAllManagers())
                .success(true)
                .build();
    }

    @GetMapping("/{managerId}")
    public ApiResponse<ManagerResponse> getManager(@PathVariable String managerId) {
        return ApiResponse.<ManagerResponse>builder()
                .result(managerService.getManager(managerId))
                .success(true)
                .build();
    }

    @DeleteMapping("/{managerId}")
    public ApiResponse<Void> deleteManager(@PathVariable String managerId) {
        managerService.deleteManager(managerId);
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }


}
