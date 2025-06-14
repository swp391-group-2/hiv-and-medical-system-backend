package com.swp391_se1866_group2.hiv_and_medical_system.appointment.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
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
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentCreationRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.createAppointment(request))
                .success(true)
                .build();
    }

    @Operation(summary = "Xem chi tiết lịch hẹn")
    @GetMapping("/appointments/{appointmentId}")
    public ApiResponse<AppointmentLabSampleResponse> getAppointmentById(@PathVariable("appointmentId") int appointmentId) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .result(appointmentService.getAppointmentById(appointmentId))
                .success(true)
                .build();
    }

    @Operation(summary = "Lấy danh sách lịch hẹn")
    @GetMapping("/appointments")
    public ApiResponse<List<AppointmentLabSampleResponse>> getAllAppointments() {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .result(appointmentService.getAllAppointments())
                .success(true)
                .build();
    }

    @Operation(summary = "Check-in bệnh nhân đến khám")
    @PostMapping("/appointments/{appointmentId}/check-in")
    public ApiResponse<AppointmentLabSampleResponse> checkinAppointment(@PathVariable("appointmentId") int appointmentId, @RequestBody LabSampleCreationRequest request) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .result(appointmentService.checkinAppointment(appointmentId,request ))
                .success(true)
                .build();
    }

    @Operation(summary = "Cập nhật kết quả xét nghiệm")
    @PutMapping("/lab-samples/{sampleId}/results")
    public ApiResponse<LabResultResponse> updateLabResult(@PathVariable("sampleId") int sampleId, @RequestBody LabResultUpdateRequest request) {
        return ApiResponse.<LabResultResponse>builder()
                .success(true)
                .result(appointmentService.updateLabResultAppointment(sampleId, request))
                .build();
    }

    @Operation(summary = "Chọn phác đồ điều trị")
    @PostMapping("/appointments/{appointmentId}/prescription/{prescriptionId}")
    public ApiResponse<PrescriptionResponse> choosePrescription (@PathVariable("appointmentId") int appointmentId, @PathVariable("prescriptionId") int prescriptionId, @RequestBody String note) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(appointmentService.choosePrescription(prescriptionId,appointmentId, note))
                .build();
    }

    @Operation(summary = "Lấy danh sách lịch appointment theo status")
    @GetMapping("/appointments/status/{status}")
    public ApiResponse<List<AppointmentLabSampleResponse>> getAppointmentsByStatus(@PathVariable("status") String status) {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .result(appointmentService.getAllAppointmentsByStatus(status))
                .success(true)
                .build();
    }



}