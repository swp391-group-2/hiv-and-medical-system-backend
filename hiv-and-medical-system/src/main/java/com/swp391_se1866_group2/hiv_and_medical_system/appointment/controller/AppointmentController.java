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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {
    AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentCreationRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.createAppointment(request))
                .success(true)
                .build();
    }

    @GetMapping("/appointments/{appointmentId}")
    public ApiResponse<AppointmentLabSampleResponse> getAppointmentById(@PathVariable("appointmentId") int appointmentId) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .result(appointmentService.getAppointmentById(appointmentId))
                .success(true)
                .build();
    }

    @GetMapping("/appointments")
    public ApiResponse<List<AppointmentLabSampleResponse>> getAllAppointments() {
        return ApiResponse.<List<AppointmentLabSampleResponse>>builder()
                .result(appointmentService.getAllAppointments())
                .success(true)
                .build();
    }

    @PostMapping("/appointments/{appointmentId}/check-in")
    public ApiResponse<AppointmentLabSampleResponse> checkinAppointment(@PathVariable("appointmentId") int appointmentId, @RequestBody LabSampleCreationRequest request) {
        return ApiResponse.<AppointmentLabSampleResponse>builder()
                .result(appointmentService.checkinAppointment(appointmentId,request ))
                .success(true)
                .build();
    }

    @PutMapping("/lab-samples/{sampleId}/results")
    public ApiResponse<LabResultResponse> updateLabResult(@PathVariable("sampleId") int sampleId, @RequestBody LabResultUpdateRequest request) {
        return ApiResponse.<LabResultResponse>builder()
                .success(true)
                .result(appointmentService.updateLabResultAppointment(sampleId, request))
                .build();
    }

    @PostMapping("/appointments/{appointmentId}/prescription/{prescriptionId}")
    public ApiResponse<PrescriptionResponse> choosePrescription (@PathVariable("appointmentId") int appointmentId, @PathVariable("prescriptionId") int prescriptionId) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .result(appointmentService.choosePrescription(prescriptionId,appointmentId))
                .build();
    }




}