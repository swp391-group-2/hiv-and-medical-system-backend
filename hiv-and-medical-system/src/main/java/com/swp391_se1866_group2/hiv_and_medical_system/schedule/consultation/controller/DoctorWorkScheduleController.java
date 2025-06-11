package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service.DoctorWorkScheduleService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service.ScheduleSlotService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorWorkScheduleController {
    DoctorWorkScheduleService scheduleService;
    ScheduleSlotService scheduleSlotService;

    @GetMapping("/schedules")
    public ApiResponse<List<DoctorWorkScheduleResponse>> getAllDoctorWorkSchedule(){
        return ApiResponse.<List<DoctorWorkScheduleResponse>>builder()
                .success(true)
                .result(scheduleService.getAllDoctorWorkSchedule())
                .build();
    }

    @PostMapping("/{doctorId}/schedules")
    public ApiResponse<DoctorWorkScheduleResponse> createSchedule(@PathVariable("doctorId") String doctorId
            , @RequestBody @Valid ScheduleCreationRequest request){
       return ApiResponse.<DoctorWorkScheduleResponse>builder()
               .success(true)
               .result(scheduleService.createDoctorSchedule(doctorId, request))
               .build();
    }

    @PostMapping("/{doctorId}/schedules/bulk")
    public ApiResponse<List<DoctorWorkScheduleResponse>> createScheduleBulk(@PathVariable("doctorId") String doctorId
            , @RequestBody @Valid ScheduleCreationRequest request){
        return ApiResponse.<List<DoctorWorkScheduleResponse>>builder()
                .success(true)
                .result(scheduleService.createDoctorScheduleBulk(doctorId, request))
                .build();
    }

    @GetMapping("/{doctorId}/schedules/date")
    public ApiResponse<List<ScheduleResponse>> getAllSchedulesByDate(@PathVariable("doctorId") String doctorId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ApiResponse.<List<ScheduleResponse>>builder()
                .result(scheduleService.getDoctorWorkScheduleByDate(doctorId, date))
                .success(true)
                .build();
    }

    @GetMapping("/{doctorId}/schedules")
    public ApiResponse<List<ScheduleResponse>> getAllSchedules(@PathVariable("doctorId") String doctorId){
        return ApiResponse.<List<ScheduleResponse>>builder()
                .result(scheduleService.getDoctorWorkScheduleByDoctorId(doctorId))
                .success(true)
                .build();
    }

    @GetMapping("/me/schedules")
    public ApiResponse<List<ScheduleResponse>> getMySchedules(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return ApiResponse.<List<ScheduleResponse>>builder()
                .success(true)
                .result(scheduleService.getDWScheduleByTokenAndBetweenDate(startDate, endDate))
                .build();
    }

    @PutMapping("/{doctorId}/schedules/date")
    public ApiResponse<DoctorWorkScheduleResponse> updateSchedules(@PathVariable("doctorId") String doctorId, @RequestBody @Valid ScheduleUpdateRequest request, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ApiResponse.<DoctorWorkScheduleResponse>builder()
                .success(true)
                .result(scheduleService.updateDoctorSchedule(doctorId, request, date))
                .build();
    }

    @PutMapping("/schedules/{scheduleSlotId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ApiResponse<ScheduleSlotResponse> updateScheduleSlot (@PathVariable("scheduleSlotId") int scheduleSlotId){
        return ApiResponse.<ScheduleSlotResponse>builder()
                .success(true)
                .result(scheduleSlotService.updateScheduleSlotStatus(scheduleSlotId))
                .build();
    }



}
