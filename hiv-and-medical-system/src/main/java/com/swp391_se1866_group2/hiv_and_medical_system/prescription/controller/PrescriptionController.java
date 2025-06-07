package com.swp391_se1866_group2.hiv_and_medical_system.prescription.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
