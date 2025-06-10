package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service.LabTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LabTestController {
    LabTestService labTestService;

    @PostMapping("/tests")
    public ApiResponse<LabTestResponse> createLabTest(@RequestBody @Valid LabTestCreationRequest request) {
        return ApiResponse.<LabTestResponse>builder()
                .success(true)
                .result(labTestService.createLabTest(request))
                .build();
    }

    @GetMapping("/tests")
    public ApiResponse<List<LabTestResponse>> getAllLabTests() {
        return ApiResponse.<List<LabTestResponse>>builder()
                .success(true)
                .result(labTestService.getAllLabTests())
                .build();
    }

    @GetMapping("/tests/{labTestName}")
    public ApiResponse<List<LabTestResponse>> getLabTestByName(@PathVariable String labTestName) {
        return ApiResponse.<List<LabTestResponse>>builder()
                .success(true)
                .result(labTestService.getLabTestByName(labTestName))
                .build();
    }

    @PutMapping("/tests/{labTestId}/{labTestParameterId}")
    public ApiResponse<LabTestParameterResponse> updateLabTestParameter(
            @PathVariable int labTestId,
            @PathVariable int labTestParameterId,
            @RequestBody @Valid LabTestParameterUpdateRequest request) {
        return ApiResponse.<LabTestParameterResponse>builder()
                .success(true)
                .result(labTestService.updateLabTestParameter(labTestId, labTestParameterId, request))
                .build();
    }
}
