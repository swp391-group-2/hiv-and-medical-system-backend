package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequestBulk;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response.LabTestSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service.LabTestSlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/test/schedules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabTestSlotController {
    LabTestSlotService labTestSlotService;

    @PostMapping
    public ApiResponse<List<LabTestSlotResponse>> createLabTestSlotBulk (@RequestBody LabTestSlotCreationRequest request) {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .result(labTestSlotService.createLabTestSlotBulk(request))
                .success(true)
                .build();
    }

    @PostMapping("/date")
    public ApiResponse<List<List<LabTestSlotResponse>>> createLabTestSlotBulkBetween (@RequestBody LabTestSlotCreationRequestBulk request,
             @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
             @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            return ApiResponse.<List<List<LabTestSlotResponse>>>builder()
                    .result(labTestSlotService.createLabTestSlotBulkBetweenDate(request,startDate,endDate))
                    .success(true)
                    .build();
    }

    @GetMapping
    public ApiResponse<List<LabTestSlotResponse>> getLabTestSlots () {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .result(labTestSlotService.getAllLabTestSlots())
                .success(true)
                .build();
    }

    @GetMapping("/date")
    public ApiResponse<List<LabTestSlotResponse>> getLabTestSlotsByDate (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .result(labTestSlotService.getAllLabTestSlotByDate(date))
                .success(true)
                .build();
    }

    @GetMapping("/{labTestSlotId}")
    public ApiResponse<LabTestSlotResponse> getLabTestSlotById (@PathVariable int labTestSlotId) {
        return ApiResponse.<LabTestSlotResponse>builder()
                .result(labTestSlotService.getLabTestSlotById(labTestSlotId))
                .success(true)
                .build();
    }
}
