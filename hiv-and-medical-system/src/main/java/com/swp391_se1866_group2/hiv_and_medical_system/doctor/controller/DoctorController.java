package com.swp391_se1866_group2.hiv_and_medical_system.doctor.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Doctor API", description = "Quản lý thông tin bác sĩ")
public class DoctorController {
    DoctorService doctorService;

    @PostMapping
    @Operation(summary = "Tạo mới bác sĩ")
    public ApiResponse<DoctorResponse> createDoctor(@RequestBody @Valid DoctorCreationRequest request){
        return ApiResponse.<DoctorResponse>builder()
                .data(doctorService.createDoctorAccount(request))
                .success(true)
                .build();
    }

    @GetMapping()
    @Operation(summary = "Lấy danh sách bác sĩ")
    public ApiResponse<List<DoctorResponse>> getDoctors(){
        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .data(doctorService.getAllDoctor())
                .build();
    }

    @GetMapping("/{doctorId}")
    @Operation(summary = "Xem thông tin bác sĩ")
    public ApiResponse<DoctorResponse> getDoctorById(@PathVariable String doctorId){
        return ApiResponse.<DoctorResponse>builder()
                .data(doctorService.getDoctorResponseById(doctorId))
                .success(true)
                .build();
    }

    @GetMapping("myInfo")
    @Operation(summary = "Lấy thông tin bác sĩ bằng token")
    public ApiResponse<DoctorResponse> getDoctorInfo(){
        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(doctorService.getDoctorProfileByToken())
                .build();
    }

    @GetMapping("/doctorProfile/{email}")
    @Operation(summary = "Lấy thông tin bác sĩ bằng email")
    public ApiResponse<DoctorResponse> getDoctorProfileByEmail(@PathVariable String email) {
       return ApiResponse.<DoctorResponse>builder()
               .success(true)
               .data(doctorService.getDoctorByEmail(email))
               .build();
    }

    @GetMapping("/top")
    @Operation(summary = "Lấy top 4 thông tin bác sĩ có đánh giá cao nhất")
    public ApiResponse<List<DoctorResponse>> getTopDoctors(){
        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .data(doctorService.getTopDoctorsForHome())
                .build();
    }

}
