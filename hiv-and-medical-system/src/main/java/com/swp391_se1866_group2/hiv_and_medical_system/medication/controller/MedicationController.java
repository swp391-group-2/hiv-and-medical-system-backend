package com.swp391_se1866_group2.hiv_and_medical_system.medication.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.service.MedicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Medication API", description = "Quản lý thông tin thuốc")
public class MedicationController {
    MedicationService medicationService;

    @PostMapping
    @Operation(summary = "Thêm thuốc mới")
    public ApiResponse<MedicationResponse> createMedication(@RequestBody @Valid MedicationRequest request) {
        return ApiResponse.<MedicationResponse>builder()
                .success(true)
                .result(medicationService.createMedication(request))
                .build();
    }

    @Operation(summary = "Lấy danh mục thuốc ")
    @GetMapping
    public ApiResponse<List<MedicationResponse>> getAllMedications() {
        return ApiResponse.<List<MedicationResponse>>builder()
                .success(true)
                .result(medicationService.getAllMedications())
                .build();
    }

    @Operation(summary = "Lấy thông tin thuốc")
    @GetMapping("/{medicationId}")
    public ApiResponse<MedicationResponse> getMedication(@PathVariable int medicationId) {
        return ApiResponse.<MedicationResponse>builder()
                .result(medicationService.getMedication(medicationId))
                .success(true)
                .build();
    }

    @Operation(summary = "Cập nhật thông tin thuốc")
    @PutMapping("/{medicationId}")
    public ApiResponse<MedicationResponse> updateMedication (@PathVariable int medicationId, @RequestBody MedicationUpdateRequest request){
        return ApiResponse.<MedicationResponse>builder()
                .result(medicationService.updateMedication(medicationId, request))
                .success(true)
                .build();
    }


}
