package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.service.LabSampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lab/samples")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "LabSample API", description = "Quản lý thông tin mẫu xét nghiệm")
public class LabSampleController {
    LabSampleService labSampleService;

    @PostMapping
    @Operation(summary = "Thêm mẫu xét nghiệm mới")
    public ApiResponse<LabSampleResponse> createLabSample (@RequestBody LabSampleCreationRequest request) {
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.createLabSample(request))
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách mẫu xét nghiệm")
    public ApiResponse<List<LabSampleResponse>> getAllLabSamples () {
        return ApiResponse.<List<LabSampleResponse>>builder()
                .success(true)
                .result(labSampleService.getLabSamples())
                .build();
    }

    @GetMapping("/{labSampleId}")
    @Operation(summary = "Xem thông tin mẫu xét nghiệm")
    public ApiResponse<LabSampleResponse> getLabSampleById (@PathVariable int labSampleId) {
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.getLabSampleById(labSampleId))
                .build();
    }

    @PutMapping("/{labSampleId}")
    @Operation(summary = "Cập nhật mẫu xét nghiệm")
    public ApiResponse<LabSampleResponse> updateLabSampleById (@PathVariable int labSampleId,
                                                               @RequestBody LabSampleUpdateRequest request){
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.upDateSampleLabById(labSampleId, request))
                .build();
    }


}
