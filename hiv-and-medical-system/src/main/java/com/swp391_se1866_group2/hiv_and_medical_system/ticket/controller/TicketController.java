package com.swp391_se1866_group2.hiv_and_medical_system.ticket.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TicketType;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.dto.response.TicketResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/patients")
public class TicketController {
    TicketService ticketService;

    @GetMapping("/me/tickets")
    public ApiResponse<List<TicketResponse>> getAllTicketsByPatientToken (){
        return ApiResponse.<List<TicketResponse>>builder()
                .data(ticketService.getListTicketByPatientToken())
                .success(true)
                .build();
    }

    @GetMapping("/me/tickets/status")
    public ApiResponse<TicketResponse> getAllTicketsByStatusAndToken (@RequestParam String status){
        return ApiResponse.<TicketResponse>builder()
                .success(true)
                .data(ticketService.getTicketByTypeAndPatientToken(TicketType.valueOf(status.toUpperCase())))
                .build();
    }

    @GetMapping("/{patientId}/tickets")
    public ApiResponse<List<TicketResponse>> getAllTicketsByPatientId (@PathVariable("patientId") String patientId){
        return ApiResponse.<List<TicketResponse>>builder()
                .data(ticketService.getListTicketByPatientId(patientId))
                .success(true)
                .build();
    }

    @GetMapping("/{patientId}/tickets/status")
    public ApiResponse<TicketResponse> getTicketByPatientIdAndStatus (@PathVariable("patientId") String patientId, @RequestParam String status){
        return ApiResponse.<TicketResponse>builder()
                .success(true)
                .data(ticketService.getTicketByTypeAndPatientId(patientId, TicketType.valueOf(status.toUpperCase())))
                .build();
    }


}
