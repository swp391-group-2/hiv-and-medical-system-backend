package com.swp391_se1866_group2.hiv_and_medical_system.user.controller;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Quản lý các user (Manager, Staff, Lab Technician)")
public class UserController {
    private final UserService userService;

    // MANAGER
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/managers")
    @Operation(summary = "Tạo manager mới")
    public ApiResponse<UserResponse> createManager(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userService.createUser(request, "MANAGER"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers")
    @Operation(summary = "Lấy danh sách các manager")
    public ApiResponse<List<UserResponse>> getAllManagers() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getAllUsers(Role.MANAGER))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy thông tin manager")
    @GetMapping("/managers/{managerId}")
    public ApiResponse<UserResponse> getManager(@PathVariable String managerId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(managerId))
                .success(true)
                .build();
    }


    //STAFF
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/staffs")
    @Operation(summary = "Tạo staff mới")
    public ApiResponse<UserResponse> createStaff(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userService.createUser(request, "STAFF"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/staffs")
    @Operation(summary = "Lấy danh sách các staff")
    public ApiResponse<List<UserResponse>> getAllStaffs() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getAllUsers(Role.STAFF))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/staffs/{staffId}")
    @Operation(summary = "Lấy thông tin staff")
    public ApiResponse<UserResponse> getStaff(@PathVariable String staffId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(staffId))
                .success(true)
                .build();
    }


    // LAB_TECHNICIAN
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/lab-technicians")
    @Operation(summary = "Tạo lab technician mới")
    public ApiResponse<UserResponse> createLabTechnician(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userService.createUser(request, "LAB_TECHNICIAN"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/lab-technicians")
    @Operation(summary = "Lấy danh sách các lab technician")
    public ApiResponse<List<UserResponse>> getAllLabTechnicians() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getAllUsers(Role.LAB_TECHNICIAN))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/lab-technicians/{labTechnicianId}")
    @Operation(summary = "Lấy thông tin lab technician")
    public ApiResponse<UserResponse> getLabTechnician(@PathVariable String labTechnicianId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(labTechnicianId))
                .success(true)
                .build();
    }



}
