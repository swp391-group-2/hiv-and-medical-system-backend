package com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.service.PrescriptionItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription-items")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PrescriptionItemController {
    PrescriptionItemService prescriptionItemService;

    @PostMapping
    public ApiResponse<PrescriptionItemResponse> createPrescriptionItem(@RequestBody @Valid PrescriptionItemRequest request) {
        return ApiResponse.<PrescriptionItemResponse>builder()
                .success(true)
                .result(prescriptionItemService.createPrescriptionItem(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PrescriptionItemResponse>> getAllPrescriptionItems() {
        return ApiResponse.<List<PrescriptionItemResponse>>builder()
                .success(true)
                .result(prescriptionItemService.getAllPrescriptionItems())
                .build();
    }

    @GetMapping("{prescriptionItemId}")
    public ApiResponse<PrescriptionItemResponse> getPrescriptionItem(@PathVariable int prescriptionItemId) {
        return ApiResponse.<PrescriptionItemResponse>builder()
                .success(true)
                .result(prescriptionItemService.getPrescriptionItem(prescriptionItemId))
                .build();
    }

    @PutMapping("{prescriptionItemId}")
    public ApiResponse<PrescriptionItemResponse> updatePrescriptionItem(@PathVariable int prescriptionItemId, @RequestBody @Valid PrescriptionItemUpdateRequest request) {
        return ApiResponse.<PrescriptionItemResponse>builder()
                .success(true)
                .result(prescriptionItemService.updatePrescriptionItem(prescriptionItemId, request))
                .build();
    }
}
