package com.swp391_se1866_group2.hiv_and_medical_system.dashboard.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.dashboard.service.DashBoardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class DashBoardController {
    DashBoardService dashBoardService;

    @GetMapping("/stats")
    public ApiResponse <List<StatsResponse>> getStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate milestone){
        return ApiResponse.<List<StatsResponse>>builder()
                .success(true)
                .data(dashBoardService.getAllStats(milestone))
                .build();

    }

    @GetMapping("/appointment-stats/average")
    public ApiResponse <AverageAppointmentsResponse> getAverageAppointmentStats(){
        return ApiResponse.<AverageAppointmentsResponse>builder()
                .success(true)
                .data(dashBoardService.getAverageAppointment())
                .build();

    }

    @GetMapping("/appointment-stats/max-min")
    public ApiResponse <MaxMinAppointmentStatsResponse> getMaxMinAppointmentStats(){
        return ApiResponse.<MaxMinAppointmentStatsResponse>builder()
                .success(true)
                .data(dashBoardService.getMaxMinAppointment())
                .build();

    }

    @GetMapping("/appointment-stats/cancelled")
    public ApiResponse <CancelledAppointmentResponse> getCancelledAppointmentStats(){
        return ApiResponse.<CancelledAppointmentResponse>builder()
                .success(true)
                .data(dashBoardService.cancelledAppointment())
                .build();

    }

    @GetMapping("/appointment-stats")
    public ApiResponse <AppointmentStatisticResponse> getAllAppointmentStats(){
        return ApiResponse.<AppointmentStatisticResponse>builder()
                .success(true)
                .data(dashBoardService.getAllAppointmentStatistics())
                .build();

    }

    @GetMapping("/service-type-stats")
    public ApiResponse <List<ServiceAppointmentStats>> getAllServiceAppointmentStats(){
        return ApiResponse.<List<ServiceAppointmentStats>>builder()
                .success(true)
                .data(dashBoardService.getAllServiceAppointmentStats())
                .build();
    }



}
