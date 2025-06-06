package com.swp391_se1866_group2.hiv_and_medical_system.user.controller;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // MANAGER
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/managers")
    public ApiResponse<UserResponse> createManager(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .result(userService.createUser(request, "MANAGER"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers")
    public ApiResponse<List<UserResponse>> getAllManagers() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .result(userService.getAllUsers(Role.MANAGER))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers/{managerId}")
    public ApiResponse<UserResponse> getManager(@PathVariable String managerId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(managerId))
                .success(true)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/managers/{managerId}")
    public ApiResponse<Void> deleteManager(@PathVariable String managerId) {
        userService.deleteUser(managerId);
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

    //STAFF
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/staffs")
    public ApiResponse<UserResponse> createStaff(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .result(userService.createUser(request, "STAFF"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/staffs")
    public ApiResponse<List<UserResponse>> getAllStaffs() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .result(userService.getAllUsers(Role.STAFF))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/staffs/{staffId}")
    public ApiResponse<UserResponse> getStaff(@PathVariable String staffId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(staffId))
                .success(true)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/staffs/{staffId}")
    public ApiResponse<Void> deleteStaff(@PathVariable String staffId) {
        userService.deleteUser(staffId);
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

    // LAB_TECHNICIAN
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/lab-technicians")
    public ApiResponse<UserResponse> createLabTechnician(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .result(userService.createUser(request, "LAB_TECHNICIAN"))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/lab-technicians")
    public ApiResponse<List<UserResponse>> getAllLabTechnicians() {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .result(userService.getAllUsers(Role.LAB_TECHNICIAN))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/lab-technicians/{labTechnicianId}")
    public ApiResponse<UserResponse> getLabTechnician(@PathVariable String labTechnicianId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(labTechnicianId))
                .success(true)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/lab-technicians/{labTechnicianId}")
    public ApiResponse<Void> deleteLabTechnician(@PathVariable String labTechnicianId) {
        userService.deleteUser(labTechnicianId);
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

}
