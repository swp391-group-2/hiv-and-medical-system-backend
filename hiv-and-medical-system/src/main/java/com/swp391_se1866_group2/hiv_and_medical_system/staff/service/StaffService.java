package com.swp391_se1866_group2.hiv_and_medical_system.staff.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.StaffMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response.StaffResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
}
