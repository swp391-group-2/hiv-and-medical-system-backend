package com.swp391_se1866_group2.hiv_and_medical_system.prescription.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class PrescriptionController {
    PrescriptionService prescriptionService;

    @PostMapping
    public ApiResponse<PrescriptionResponse> createPrescription(@RequestBody @Valid PrescriptionCreationRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(prescriptionService.createPrescription(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .result(prescriptionService.getAllPrescriptions())
                .build();
    }

    @GetMapping("{prescriptionName}")
    public ApiResponse<List<PrescriptionResponse>> getPrescriptionByName(@PathVariable String prescriptionName) {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .result(prescriptionService.getPrescriptionByName(prescriptionName))
                .build();
    }


    @PutMapping("/{prescriptionId}/{prescriptionItemId}")
    public ApiResponse<PrescriptionItemResponse> updatePrescription(@PathVariable int prescriptionId, @PathVariable int prescriptionItemId, @RequestBody @Valid PrescriptionItemUpdateRequest request) {
        return ApiResponse.<PrescriptionItemResponse>builder()
                .success(true)
                .result(prescriptionService.updatePrescription(prescriptionId, prescriptionItemId, request))
                .build();
    }


}
