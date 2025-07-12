package com.swp391_se1866_group2.hiv_and_medical_system.doctor.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateDTORequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointment;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorAppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Operation(summary = "Lấy danh sách bác sĩ và tổng lịch hẹn")
    public ApiResponse<List<DoctorAppointmentResponse>> getDoctorsAppointment(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "13") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<List<DoctorAppointmentResponse>>builder()
                .success(true)
                .data(doctorService.getDoctorsAppointment(name, pageable))
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

    @GetMapping("/myInfo")
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

    @PutMapping("/{doctorId}")
    @Operation(summary = "update thông tin của bác sĩ")
    public ApiResponse<DoctorResponse> updateDoctor(@PathVariable String doctorId, @RequestBody DoctorUpdateRequest request){
        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(doctorService.updateDoctorProfile(doctorId,request))
                .build();
    }

    @PutMapping("/{doctorId}/update-by-manager")
    @Operation(summary = "update thông tin của bác sĩ bởi manager")
    public ApiResponse<DoctorResponse> updateDoctorByManager(@PathVariable String doctorId, @RequestBody DoctorUpdateDTORequest request){
        return ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(doctorService.updateDoctorProfileByManager(doctorId,request))
                .build();
    }

    @GetMapping("/count")
    @Operation(summary = "Đếm xem hiện tại có bao nhiêu bác sĩ")
    public ApiResponse<Long> countDoctors(){
        return ApiResponse.<Long>builder()
                .success(true)
                .data(doctorService.countDoctor())
                .build();
    }

    @GetMapping("/count-active")
    @Operation(summary = "Đếm xem hiện tại có bao nhiêu bác sĩ có status active")
    public ApiResponse<Long> countDoctorsActive(){
        return ApiResponse.<Long>builder()
                .success(true)
                .data(doctorService.countDoctorActive())
                .build();
    }

    @GetMapping("/top-appointmentCount")
    @Operation(summary = "Lấy các bác sĩ có nhiều người đăng kí khám cao nhất")
    public ApiResponse<List<DoctorAppointmentResponse>> getTopDoctorsAppointment(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "13") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<List<DoctorAppointmentResponse>>builder()
                .success(true)
                .data(doctorService.getTopDoctorsAppointment(name, pageable))
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm bác sĩ theo tên")
    public ApiResponse<List<DoctorResponse>> getDoctorByName(@RequestParam(required = true) String name) {
        return ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .data(doctorService.getDoctorByName(name))
                .build();
    }

}
