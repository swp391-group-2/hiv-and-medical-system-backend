package com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.request.LabTestSlotCreationRequestBulk;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.dto.response.LabTestSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service.LabTestSlotService;
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
@RequestMapping("/api/test/schedules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "LabTestSlot API", description = "Quản lý slot xét nghiệm")
public class LabTestSlotController {
    LabTestSlotService labTestSlotService;

    @PostMapping
    @Operation(summary = "Tạo danh sách slot xét nghiệm", description = "Tạo nhiều slot xét nghiệm trong 1 ngày")
    public ApiResponse<List<LabTestSlotResponse>> createLabTestSlotBulk (@RequestBody LabTestSlotCreationRequest request) {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .data(labTestSlotService.createLabTestSlotBulk(request))
                .success(true)
                .build();
    }

    @PostMapping("/date")
    @Operation(summary = "Tạo slot xét nghiệm theo khoảng thời gian chỉ định", description = "Tạo danh sách slot xét nghiệm trong nhiều ngày liên tiếp")
    public ApiResponse<List<List<LabTestSlotResponse>>> createLabTestSlotBulkBetween (@RequestBody LabTestSlotCreationRequestBulk request,
             @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
             @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            return ApiResponse.<List<List<LabTestSlotResponse>>>builder()
                    .data(labTestSlotService.createLabTestSlotBulkBetweenDate(request,startDate,endDate))
                    .success(true)
                    .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả slot xét nghiệm")
    public ApiResponse<List<LabTestSlotResponse>> getLabTestSlots () {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .data(labTestSlotService.getAllLabTestSlots())
                .success(true)
                .build();
    }

    @GetMapping("/date")
    @Operation(summary = "Lấy danh sách slot xét nghiệm theo ngày")
    public ApiResponse<List<LabTestSlotResponse>> getLabTestSlotsByDate (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.<List<LabTestSlotResponse>>builder()
                .data(labTestSlotService.getAllLabTestSlotByDate(date))
                .success(true)
                .build();
    }

    @GetMapping("/{labTestSlotId}")
    @Operation(summary = "Lấy slot xét nghiệm theo ID")
    public ApiResponse<LabTestSlotResponse> getLabTestSlotById (@PathVariable int labTestSlotId) {
        return ApiResponse.<LabTestSlotResponse>builder()
                .data(labTestSlotService.getLabTestResponseSlotById(labTestSlotId))
                .success(true)
                .build();
    }
}
