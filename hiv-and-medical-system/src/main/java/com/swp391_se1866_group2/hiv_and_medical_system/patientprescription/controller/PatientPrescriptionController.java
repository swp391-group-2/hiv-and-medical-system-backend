package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service.PatientPrescriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prescriptions/patients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientPrescriptionController {
    PatientPrescriptionService patientPrescriptionService;

    @PostMapping
    public ApiResponse<PaPrescriptionResponse> choosePatientPrescription (@RequestBody PaPrescriptionCreation request) {
        return ApiResponse.<PaPrescriptionResponse>builder()
                .data(patientPrescriptionService.createPatientPrescription(request))
                .success(true)
                .build();
    }


}
