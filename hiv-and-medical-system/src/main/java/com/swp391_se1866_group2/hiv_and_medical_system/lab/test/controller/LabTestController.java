package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service.LabTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "LabTest API", description = "Quản lý thông tin loại xét nghiệm")
public class LabTestController {
    LabTestService labTestService;

    @PostMapping("/tests")
    @Operation(summary = "Tạo loại xét nghiệm mới")
    public ApiResponse<LabTestResponse> createLabTest(@RequestBody @Valid LabTestCreationRequest request) {
        return ApiResponse.<LabTestResponse>builder()
                .success(true)
                .data(labTestService.createLabTest(request))
                .build();
    }

    @GetMapping("/tests")
    @Operation(summary = "Lấy danh sách các loại xét nghiệm")
    public ApiResponse<List<LabTestResponse>> getAllLabTests() {
        return ApiResponse.<List<LabTestResponse>>builder()
                .success(true)
                .data(labTestService.getAllLabTests())
                .build();
    }

    @GetMapping("/tests/{labTestName}")
    @Operation(summary = "Lấy danh sách loại xét nghiệm theo tên")
    public ApiResponse<List<LabTestResponse>> getLabTestByName(@PathVariable String labTestName) {
        return ApiResponse.<List<LabTestResponse>>builder()
                .success(true)
                .data(labTestService.getLabTestByName(labTestName))
                .build();
    }

    @PutMapping("/tests/{labTestId}/{labTestParameterId}")
    @Operation(summary = "Cập nhật thông số loại xét nghiệm")
    public ApiResponse<LabTestParameterResponse> updateLabTestParameter(
            @PathVariable int labTestId,
            @PathVariable int labTestParameterId,
            @RequestBody @Valid LabTestParameterUpdateRequest request) {
        return ApiResponse.<LabTestParameterResponse>builder()
                .success(true)
                .data(labTestService.updateLabTestParameter(labTestId, labTestParameterId, request))
                .build();
    }
}
