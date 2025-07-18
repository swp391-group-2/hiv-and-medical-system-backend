package com.swp391_se1866_group2.hiv_and_medical_system.appointment.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentBlockRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentCreationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentLabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabResultUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Appointment API", description = "Quản lý lịch hẹn: check-in, cập nhật kết quả và chọn phác đồ")
public class AppointmentController {
    AppointmentService appointmentService;

    @Operation(summary = "Đặt lịch hẹn")
    @PostMapping("/appointments")
    public ApiResponse<AppointmentCreationResponse> createAppointment(@RequestBody AppointmentCreationRequest request) {
        return ApiResponse.<AppointmentCreationResponse>builder()
                .data(appointmentService.createAppointment(request))
                .success(true)
                .build();
    }

    @Operation(summary = "Xem chi tiết lịch hẹn")
    @GetMapping("/appointments/{appointmentId}")
    public ApiResponse<AppointmentLabSampleResponse> getAppointmentById(@PathVariable("appointmentId") int appointmentId) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .data(appointmentService.getAppointmentById(appointmentId))
                .success(true)
                .build();
    }

    @Operation(summary = "Lấy danh sách lịch hẹn")
    @GetMapping("/appointments")
    public ApiResponse<List<AppointmentLabSampleResponse>> getAllAppointments() {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .data(appointmentService.getAllAppointments())
                .success(true)
                .build();
    }

    @Operation(summary = "Check-in bệnh nhân đến khám")
    @PostMapping("/appointments/{appointmentId}/check-in")
    public ApiResponse<AppointmentLabSampleResponse> checkinAppointment(@PathVariable("appointmentId") int appointmentId, @RequestBody LabSampleCreationRequest request) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .data(appointmentService.checkinAppointment(appointmentId,request ))
                .success(true)
                .build();
    }

    @Operation(summary = "Cập nhật kết quả xét nghiệm")
    @PutMapping("/lab-samples/{sampleId}/results")
    public ApiResponse<LabResultResponse> inputLabResult(@PathVariable("sampleId") int sampleId, @RequestBody LabResultUpdateRequest request) {
        return ApiResponse.<LabResultResponse>builder()
                .success(true)
                .data(appointmentService.inputLabResultAppointment(sampleId, request))
                .build();
    }

    @Operation(summary = "Lấy danh sách lịch appointment theo status")
    @GetMapping("/appointments/status/{status}")
    public ApiResponse<List<AppointmentLabSampleResponse>> getAppointmentsByStatus(@PathVariable("status") String status) {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .data(appointmentService.getAllAppointmentsByStatus(status))
                .success(true)
                .build();
    }
    @Operation(summary = "Xác nhận có trả kết quả lab-result về cho bệnh nhân hay không?")
    @PostMapping("/appointments/{appointmentId}/can-return")
    public ApiResponse<Boolean> canReturnAppointment(@PathVariable("appointmentId") int appointmentId, @RequestBody boolean status) {
        if(appointmentService.isResultReturnAllowed(appointmentId, status)){
            return ApiResponse.<Boolean>builder()
                    .success(true)
                    .message("Lab result can return.")
                    .data(true)
                    .build();
        }
        return ApiResponse.<Boolean>builder()
                .success(true)
                .message("Lab result can not return.")
                .data(false)
                .build();
    }


    @Operation(summary = "Lấy danh sách lịch appointment theo status của từng bác sĩ ")
    @GetMapping("/doctors/me/appointments/{status}")
    public ApiResponse<List<AppointmentLabSampleResponse>> getDoctorAppointmentStatus(@PathVariable("status") String status) {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .data(appointmentService.getAllDoctorAppointmentsByStatus(status))
                .success(true)
                .build();
    }

    @Operation(summary = "Hủy lịch đặt khám trong 24h")
    @PostMapping("/appointments/{appointmentId}/cancel")
    public ApiResponse<Boolean> cancelAppointment(@PathVariable("appointmentId") int appointmentId) {
        return ApiResponse.<Boolean>builder()
                .success(true)
                .data(appointmentService.cancelAppointment(appointmentId))
                .build();
    }

    @Operation(summary = "Thanh toán bằng ticket")
    @PostMapping("/appointments/tickets/exchange")
    public ApiResponse<Boolean> createAppointmentByTicket (@RequestBody AppointmentCreationRequest request){
        return ApiResponse.<Boolean>builder()
                .success(true)
                .data(appointmentService.createAppointmentByTicket(request))
                .build();
    }

    @Operation(summary = "Hủy lịch appointment với tư cách là manager ")
    @PutMapping("/appointments/{appointmentId}/cancel-by-manager")
    public ApiResponse<Boolean> cancelAppointmentByManager(@RequestBody AppointmentBlockRequest request) {
        return ApiResponse.<Boolean>builder()
                .success(true)
                .data(appointmentService.cancelAppointmentByManager(request))
                .build();
    }
}