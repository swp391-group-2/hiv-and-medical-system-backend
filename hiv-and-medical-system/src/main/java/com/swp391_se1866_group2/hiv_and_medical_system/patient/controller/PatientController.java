package com.swp391_se1866_group2.hiv_and_medical_system.patient.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentCreationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service.LabTestService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service.PatientPrescriptionService;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    AppointmentService appointmentService;
    PatientPrescriptionService patientPrescriptionService;
    LabTestService labTestService;

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

    @PutMapping("/{patientId}")
    @Operation(summary = "Cập nhật thông tin bệnh nhân")
    public ApiResponse<PatientResponse> updatePatientProfile (@PathVariable String patientId, @RequestBody @Valid PatientUpdateRequest request){
        return ApiResponse.<PatientResponse>builder()
                .data(patientService.updatePatientProfile(patientId, request ))
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

    @GetMapping("/{patientId}/appointments")
    @Operation(summary = "Lấy danh sách lịch đăng kí khám và xét nghiệm")
    public ApiResponse<List<AppointmentCreationResponse>> getAppointmentsByPatient(@PathVariable String patientId) {
        return ApiResponse.<List<AppointmentCreationResponse>>builder()
                .success(true)
                .data(appointmentService.getAllAppointmentByPatientId(patientId))
                .build();
    }

    @GetMapping("/{patientId}/prescriptions")
    @Operation(summary = "Lấy danh sách các đơn thuốc của bệnh nhân")
    public ApiResponse<List<PaPrescriptionResponse>> getPrescriptionsByPatient(@PathVariable String patientId) {
        return ApiResponse.<List<PaPrescriptionResponse>>builder()
                .success(true)
                .data(patientPrescriptionService.getPatientPrescriptionByPatientId(patientId))
                .build();
    }

    @GetMapping("/{patientId}/labResult")
    @Operation(summary = "Lấy danh sách các kết quả xét nghiệm của bệnh nhân")
    public ApiResponse<List<LabResultResponse>> getLabResultsByPatient(@PathVariable String patientId) {
        return ApiResponse.<List<LabResultResponse>>builder()
                .success(true)
                .data(labTestService.getLabResultByPatientId(patientId))
                .build();
    }
}
