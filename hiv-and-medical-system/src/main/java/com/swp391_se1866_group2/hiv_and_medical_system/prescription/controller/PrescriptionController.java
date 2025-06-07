package com.swp391_se1866_group2.hiv_and_medical_system.prescription.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class PrescriptionController {
    PrescriptionService prescriptionService;

    @PostMapping
    public ApiResponse<PrescriptionResponse>createPrescription(@RequestBody @Valid PrescriptionRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(prescriptionService.createPrescription(request))
                .build();
    }

    @GetMapping("{prescriptionId}")
    public ApiResponse<PrescriptionResponse> getPrescription(@PathVariable String prescriptionId) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(prescriptionService.getPrescription(prescriptionId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .result(prescriptionService.getAllPrescriptions())
                .build();
    }

    @PutMapping("{prescriptionId}")
    public ApiResponse<PrescriptionResponse> updatePrescription(@PathVariable String prescriptionId, @RequestBody @Valid PrescriptionUpdateRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(prescriptionService.updatePrescription(prescriptionId, request))
                .build();
    }

    @DeleteMapping("{prescriptionId}")
    public ApiResponse<Void> deletePrescription(@PathVariable String prescriptionId) {
        prescriptionService.deletePrescription(prescriptionId);
        return ApiResponse.<Void>builder()
                .success(true)
                .result(null)
                .build();
    }
}
