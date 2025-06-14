package com.swp391_se1866_group2.hiv_and_medical_system.patient.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("api/patients"))
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Patient API", description = "Quản lý thông tin bệnh nhân")
public class PatientController {
    PatientService patientService;

    @GetMapping
    @Operation(summary = "Lấy danh sách bệnh nhân")
    public ApiResponse<List<PatientResponse>> getAllPatients() {
        return ApiResponse.<List<PatientResponse>>builder()
                .data(patientService.getAllPatients())
                .success(true)
                .build();
    }

    @GetMapping("/myInfo")
    @Operation(summary = "Xem thông tin bệnh nhân bằng token")
    public ApiResponse<PatientResponse> getMyInfo() {
        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(patientService.getPatientProfileByToken())
                .build();
    }


    @GetMapping("/{patientid}")
    @Operation(summary = "Xem thông tin bệnh nhân")
    public ApiResponse<PatientResponse> getPatient(@PathVariable String patientid) {
        return ApiResponse.<PatientResponse>builder()
                .data(patientService.getPatient(patientid))
                .success(true)
                .build();
    }

    @PutMapping("/{patientid}")
    @Operation(summary = "Cập nhật thông tin bệnh nhân")
    public ApiResponse<PatientResponse> updatePatientProfile (@PathVariable String patientid, @RequestBody PatientUpdateRequest request){
        return ApiResponse.<PatientResponse>builder()
                .data(patientService.updatePatientProfile(patientid, request ))
                .success(true)
                .build();
    }

    @GetMapping("/patientProfile/{email}")
    public ApiResponse<PatientResponse> getPatientProfileByEmail(@PathVariable String email) {
        return ApiResponse.<PatientResponse>builder()
                .data(patientService.getPatientByEmail(email))
                .success(true)
                .build();
    }

}
