package com.swp391_se1866_group2.hiv_and_medical_system.patient.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("api/patients"))
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientController {
    PatientService patientService;

    @PutMapping("/{patientid}")
    public ApiResponse<PatientResponse> updatePatientProfile (@PathVariable String patientid, @RequestBody PatientUpdateRequest request){
        return ApiResponse.<PatientResponse>builder()
                .result(patientService.updatePatientProfile(patientid, request ))
                .success(true)
                .build();
    }

}
