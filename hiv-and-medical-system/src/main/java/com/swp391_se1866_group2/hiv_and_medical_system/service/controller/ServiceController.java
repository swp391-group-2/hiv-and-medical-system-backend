package com.swp391_se1866_group2.hiv_and_medical_system.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.blogpost.dto.request.BlogPostUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request.ServiceCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request.ServiceUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Service API", description = "Quản lý thông tin dịch vụ")
public class ServiceController {
    ServiceService serviceService;
    ObjectMapper objectMapper;

    @PostMapping
    @Operation(summary = "Tạo dịch vụ mới")
    public ApiResponse<ServiceResponse> createService (@RequestBody ServiceCreationRequest request){
        return ApiResponse.<ServiceResponse>builder()
                .data(serviceService.createService(request))
                .success(true)
                .build();
    }

    @PutMapping("/{serviceId}")
    @Operation(summary = "Cập nhật dịch vụ theo ID")
    public ApiResponse<ServiceResponse> updateService (@PathVariable("serviceId") int serviceId ,
                                                       @RequestParam("data") String jsonData, @RequestParam(value = "file",  required = false) MultipartFile file) throws JsonProcessingException {
        ServiceUpdateRequest request = objectMapper.readValue(jsonData, ServiceUpdateRequest.class);
        return ApiResponse.<ServiceResponse>builder()
                .data(serviceService.updateService(serviceId, request, file))
                .success(true)
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả dịch vụ")
    public ApiResponse<List<ServiceResponse>> getAllServices (){
        return ApiResponse.<List<ServiceResponse>>builder()
                .data(serviceService.getAllServices())
                .success(true)
                .build();
    }

    @GetMapping("/{serviceId}")
    @Operation(summary = "Lấy thông tin dịch vụ theo ID")
    public ApiResponse<ServiceResponse> getService(@PathVariable("serviceId") int serviceId){
        return ApiResponse.<ServiceResponse>builder()
                .data(serviceService.getService(serviceId))
                .build();
    }


    @GetMapping("/type/{serviceType}")
    @Operation(summary = "Lấy thông tin dịch vụ theo ID")
    public ApiResponse<ServiceResponse> getService(@PathVariable("serviceType") String serviceType){
        return ApiResponse.<ServiceResponse>builder()
                .data(serviceService.getServiceByType(serviceType))
                .build();
    }

}
