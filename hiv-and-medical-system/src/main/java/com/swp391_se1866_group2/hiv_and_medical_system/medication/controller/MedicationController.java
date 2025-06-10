package com.swp391_se1866_group2.hiv_and_medical_system.medication.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.service.MedicationService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicationController {
    MedicationService medicationService;

    @PostMapping
    public ApiResponse<MedicationResponse> createMedication(@RequestBody @Valid MedicationRequest request) {
        return ApiResponse.<MedicationResponse>builder()
                .success(true)
                .result(medicationService.createMedication(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<MedicationResponse>> getAllMedications() {
        return ApiResponse.<List<MedicationResponse>>builder()
                .success(true)
                .result(medicationService.getAllMedications())
                .build();
    }

    @GetMapping("/{medicationId}")
    public ApiResponse<MedicationResponse> getMedication(@PathVariable int medicationId) {
        return ApiResponse.<MedicationResponse>builder()
                .result(medicationService.getMedication(medicationId))
                .success(true)
                .build();
    }

    @PutMapping("/{medicationId}")
    public ApiResponse<MedicationResponse> updateMedication (@PathVariable int medicationId, @RequestBody MedicationUpdateRequest request){
        return ApiResponse.<MedicationResponse>builder()
                .result(medicationService.updateMedication(medicationId, request))
                .success(true)
                .build();
    }


}
