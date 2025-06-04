package com.swp391_se1866_group2.hiv_and_medical_system.manager.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response.ManagerResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.service.ManagerService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class ManagerController {
    ManagerService managerService;

    @PostMapping
    public ApiResponse<ManagerResponse> createManager(@RequestBody UserCreationRequest request) {
        return ApiResponse.<ManagerResponse>builder()
                .success(true)
                .result(managerService.createManager(request))
                .build();
    }

}
