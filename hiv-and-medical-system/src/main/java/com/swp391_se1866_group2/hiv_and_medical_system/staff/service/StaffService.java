package com.swp391_se1866_group2.hiv_and_medical_system.staff.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.StaffMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response.StaffResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class StaffService {
    UserService userService;
    UserRepository userRepository;
    StaffMapper staffMapper;
    UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public StaffResponse createStaff(UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request, Role.STAFF.name());
        return staffMapper.toStaffResponse(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<StaffResponse> getAllStaffs() {
        return userRepository.findAll().stream()
                .filter(user -> Role.STAFF.name().equals(user.getRole()))
                .map(userMapper::toUserResponse)
                .map(staffMapper::toStaffResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public StaffResponse getStaff(String staffId) {
        User user = userRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!Role.STAFF.name().equals(user.getRole())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        UserResponse userResponse = userMapper.toUserResponse(user);
        return staffMapper.toStaffResponse(userResponse);
    }
}
