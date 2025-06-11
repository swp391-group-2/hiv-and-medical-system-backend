package com.swp391_se1866_group2.hiv_and_medical_system.appointment.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppoimentStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {
    AppointmentService appointmentService;

    @PostMapping
    public ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentCreationRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.createAppointment(request))
                .success(true)
                .build();
    }

    @GetMapping("/{appointmentId}")
    public ApiResponse<AppointmentResponse> getAppointmentById(@PathVariable("appointmentId") int appointmentId) {
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.getAppointmentById(appointmentId))
                .success(true)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .result(appointmentService.getAllAppointments())
                .success(true)
                .build();
    }

    @GetMapping("/check-in")
    public ApiResponse<List<AppointmentResponse>> getAllAppointmentsCheckin() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .result(appointmentService.getAllAppointmentsByStatus(AppoimentStatus.SCHEDULED))
                .success(true)
                .build();
    }
}