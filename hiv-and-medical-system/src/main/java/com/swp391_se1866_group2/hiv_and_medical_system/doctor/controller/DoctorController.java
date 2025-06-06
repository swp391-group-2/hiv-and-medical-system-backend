package com.swp391_se1866_group2.hiv_and_medical_system.doctor.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {
    DoctorService doctorService;

    @PostMapping
    public ApiResponse<DoctorResponse> createDoctor(@RequestBody @Valid DoctorCreationRequest request){
        return ApiResponse.<DoctorResponse>builder()
                .result(doctorService.createDoctorAccount(request, Role.DOCTOR.name()))
                .success(true)
                .build();
    }

    @GetMapping()
    public ApiResponse<List<DoctorResponse>> getDoctors(){
        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .result(doctorService.getAllDoctor())
                .build();
    }



}
