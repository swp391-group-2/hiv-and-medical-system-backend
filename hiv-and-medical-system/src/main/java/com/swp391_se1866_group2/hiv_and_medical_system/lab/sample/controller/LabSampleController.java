package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.service.LabSampleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lab/samples")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabSampleController {
    LabSampleService labSampleService;

    @PostMapping
    public ApiResponse<LabSampleResponse> createLabSample (@RequestBody LabSampleCreationRequest request) {
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.createLabSample(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<LabSampleResponse>> getAllLabSamples () {
        return ApiResponse.<List<LabSampleResponse>>builder()
                .success(true)
                .result(labSampleService.getLabSamples())
                .build();
    }

    @GetMapping("/{labSampleId}")
    public ApiResponse<LabSampleResponse> getLabSampleById (@PathVariable int labSampleId) {
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.getLabSampleById(labSampleId))
                .build();
    }

    @PutMapping("/{labSampleId}")
    public ApiResponse<LabSampleResponse> updateLabSampleById (@PathVariable int labSampleId,
                                                               @RequestBody LabSampleUpdateRequest request){
        return ApiResponse.<LabSampleResponse>builder()
                .success(true)
                .result(labSampleService.upDateSampleLabById(labSampleId, request))
                .build();
    }


}
