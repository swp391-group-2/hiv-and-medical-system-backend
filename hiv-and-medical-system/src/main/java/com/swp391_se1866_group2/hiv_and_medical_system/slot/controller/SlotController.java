package com.swp391_se1866_group2.hiv_and_medical_system.slot.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.request.SlotCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.dto.response.SlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.service.SlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SlotController {
    SlotService slotService;
    @PostMapping
    public ApiResponse<SlotResponse> createSlot (@RequestBody SlotCreationRequest request){
        return ApiResponse.<SlotResponse>builder()
                .success(true)
                .result(slotService.createSlot(request))
                .build();
    }

}
