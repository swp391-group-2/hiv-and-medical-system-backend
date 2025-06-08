package com.swp391_se1866_group2.hiv_and_medical_system.schedule.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.request.ScheduleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.service.DoctorWorkScheduleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorWorkScheduleController {
    DoctorWorkScheduleService scheduleService;

    @PostMapping("/{doctorId}/schedules")
    public ApiResponse<DoctorWorkScheduleResponse> createSchedule(@PathVariable("doctorId") String doctorId
            , @RequestBody @Valid ScheduleCreationRequest request){
       return ApiResponse.<DoctorWorkScheduleResponse>builder()
               .success(true)
               .result(scheduleService.createDoctorSchedule(doctorId, request))
               .build();
    }

    @GetMapping("/{doctorId}/schedules")
    public ApiResponse<List<DoctorWorkScheduleResponse>> getAllSchedulesByDate(@PathVariable("doctorId") String doctorId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ApiResponse.<List<DoctorWorkScheduleResponse>>builder()
                .result(scheduleService.getDoctorWorkScheduleByDate(doctorId, date))
                .success(true)
                .build();
    }





}
