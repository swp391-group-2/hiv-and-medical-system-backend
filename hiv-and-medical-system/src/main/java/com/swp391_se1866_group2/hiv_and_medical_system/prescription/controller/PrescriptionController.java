package com.swp391_se1866_group2.hiv_and_medical_system.prescription.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Prescription API", description = "Quản lý thông tin phác đồ điều trị")
public class PrescriptionController {
    PrescriptionService prescriptionService;

    @PostMapping
    @Operation(summary = "Tạo phác đồ mới")
    public ApiResponse<PrescriptionResponse> createPrescription(@RequestBody @Valid PrescriptionCreationRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .data(prescriptionService.createPrescription(request))
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách phác đồ")
    public ApiResponse<List<PrescriptionResponse>> getAllPrescriptions() {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .data(prescriptionService.getAllPrescriptions())
                .build();
    }

    @GetMapping("{prescriptionName}")
    @Operation(summary = "Xem thông tin phác đồ theo tên")
    public ApiResponse<List<PrescriptionResponse>> getPrescriptionByName(@PathVariable String prescriptionName) {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .data(prescriptionService.getPrescriptionByName(prescriptionName))
                .build();
    }


    @PutMapping("/{prescriptionId}/{prescriptionItemId}")
    @Operation(summary = "Cập nhật thông tin phác đồ")
    public ApiResponse<PrescriptionItemResponse> updatePrescription(@PathVariable int prescriptionId, @PathVariable int prescriptionItemId, @RequestBody @Valid PrescriptionItemUpdateRequest request) {
        return ApiResponse.<PrescriptionItemResponse>builder()
                .success(true)
                .data(prescriptionService.updatePrescription(prescriptionId, prescriptionItemId, request))
                .build();
    }


}
